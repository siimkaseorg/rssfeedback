package ee.valiit.rssfeedback.controller.rss;


import com.rometools.rome.feed.module.Module;
import com.rometools.modules.mediarss.MediaEntryModule;
import com.rometools.modules.mediarss.MediaModule;
import com.rometools.modules.mediarss.types.Thumbnail;


import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import ee.valiit.rssfeedback.controller.rss.dto.RssItem;
import ee.valiit.rssfeedback.infrastructure.exception.PrimaryKeyNotFoundException;
import ee.valiit.rssfeedback.persitence.article.Article;
import ee.valiit.rssfeedback.persitence.article.ArticleMapper;
import ee.valiit.rssfeedback.persitence.article.ArticleRepository;
import ee.valiit.rssfeedback.persitence.category.Category;
import ee.valiit.rssfeedback.persitence.portal.Portal;
import ee.valiit.rssfeedback.persitence.portal.PortalRepository;
import ee.valiit.rssfeedback.persitence.portalcategory.PortalCategory;
import ee.valiit.rssfeedback.persitence.portalcategory.PortalCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RssService {

    private final PortalRepository portalRepository;
    private final PortalCategoryRepository portalCategoryRepository;
    private final ArticleMapper articleMapper;
    private final ArticleRepository articleRepository;

    // Uncomment when you’re ready to schedule
    // @Scheduled(cron = "0 */15 * * * *")
    public void importAllArticles() {
        for (Portal portal : portalRepository.findAll()) {
            if (portal.getXmlLink() != null && !portal.getXmlLink().isEmpty()) {
                importPortalArticles(portal);
            }
        }
    }

    public void importPortalArticles(Integer portalId) {
        Portal portal = portalRepository.findById(portalId)
                .orElseThrow(() -> new PrimaryKeyNotFoundException("portalId", portalId));
        importPortalArticles(portal);
    }

    public void importPortalArticles(Portal portal) {
        List<RssItem> rssItems = fetchRssFeed(portal.getXmlLink());

        for (RssItem rssItem : rssItems) {
            Optional<PortalCategory> optionalPortalCategory =
                    portalCategoryRepository.findPortalCategoryBy(portal, rssItem.getCategoryName());

            if (optionalPortalCategory.isPresent()) {
                Category category = optionalPortalCategory.get().getCategory();

                boolean articleExists = articleRepository.articleExistsBy(rssItem.getGuid());

                if (!articleExists) {
                    Article article = articleMapper.toArticle(rssItem);
                    article.setPortal(portal);
                    article.setCategory(category);
                    articleRepository.save(article);
                }
            }
        }
    }

    public List<RssItem> fetchRssFeed(String feedUrl) {
        try {
            log.info("Attempting to fetch RSS feed from: {}", feedUrl);

            // Check content type (for debugging; don’t hard-fail on it)
            String peekContent = peekContent(feedUrl);
            String contentType = getContentType(feedUrl);
            log.info("Content-Type for {}: {}", feedUrl, contentType);

            if (contentType != null && contentType.toLowerCase().contains("text/html")) {
                log.warn("URL {} returns HTML Content-Type; this might not be a pure RSS endpoint.", feedUrl);
                // You can throw here if you really want to enforce XML:
                // throw new RuntimeException("The provided URL returns HTML content instead of RSS feed. Please check the URL.");
            }

            SyndFeedInput input = new SyndFeedInput();

            SyndFeed feed;
            try (XmlReader reader = new XmlReader(new URL(feedUrl))) {
                feed = input.build(reader);
            }

            log.info("=== RSS FEED INFO ===");
            log.info("Title: {}", feed.getTitle());
            log.info("Description: {}", feed.getDescription());
            log.info("Link: {}", feed.getLink());
            log.info("Published Date: {}", feed.getPublishedDate());
            log.info("Number of entries: {}", feed.getEntries().size());

            List<RssItem> rssItems = feed.getEntries().stream()
                    .limit(50) // you can adjust the limit or remove it
                    .map(this::convertToRssItem)
                    .collect(Collectors.toList());

            log.info("=== RECENT ARTICLES (first {}) ===", rssItems.size());
            rssItems.forEach(item -> {
                log.info("Title: {}", item.getTitle());
                String desc = item.getDescription();
                if (desc != null && desc.length() > 100) {
                    desc = desc.substring(0, 100) + "...";
                }
                log.info("Description: {}", desc);
                log.info("---");
            });

            return rssItems;

        } catch (MalformedURLException e) {
            log.error("Malformed RSS URL: {}", feedUrl, e);
            return new ArrayList<>();

        } catch (FeedException e) {
            // Rome couldn't parse the feed as valid XML/RSS
            log.error("Feed parsing error for URL {}: {}", feedUrl, e.getMessage(), e);
            throw new RuntimeException("Failed to parse RSS feed at " + feedUrl, e);

        } catch (IOException e) {
            // Network / IO issues
            log.error("I/O error while fetching RSS feed from {}: {}", feedUrl, e.getMessage(), e);
            throw new RuntimeException("I/O error while fetching RSS feed from " + feedUrl, e);

        } catch (Exception e) {
            // Catch-all – no more e.getMessage().contains(...) nonsense
            log.error("Unexpected error fetching RSS feed from {}", feedUrl, e);
            throw new RuntimeException("Failed to fetch RSS feed from " + feedUrl, e);
        }
    }

    private String getContentType(String feedUrl) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(feedUrl).openConnection();
            connection.setRequestMethod("HEAD");
            connection.setRequestProperty("User-Agent", "RSS-Reader/1.0");
            connection.connect();

            String contentType = connection.getContentType();
            connection.disconnect();

            return contentType;
        } catch (IOException e) {
            log.warn("Could not determine content type for {}: {}", feedUrl, e.getMessage());
            return null;
        }
    }

    // Optional helper: peek at first lines of response for debugging
    public String peekContent(String feedUrl) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(feedUrl).openConnection();
            connection.setRequestProperty("User-Agent", "RSS-Reader/1.0");

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {

                StringBuilder content = new StringBuilder();
                String line;
                int lineCount = 0;

                while ((line = reader.readLine()) != null && lineCount < 20) {
                    content.append(line).append("\n");
                    lineCount++;
                }

                return content.toString();
            }
        } catch (IOException e) {
            return "Error reading content: " + e.getMessage();
        }
    }

    private RssItem convertToRssItem(SyndEntry entry) {
        // Category (optional)
        String categoryName = null;
        if (entry.getCategories() != null && !entry.getCategories().isEmpty()) {
            categoryName = entry.getCategories().get(0).getName();
        }

        // Image / enclosure (optional; many feeds don’t have any)
        String imageLink = extractImageLink(entry);

        // GUID – fall back to link if null/blank
        String guid = entry.getUri();
        if (guid == null || guid.isBlank()) {
            guid = entry.getLink();
        }

        // Date – fall back to updatedDate if publishedDate is null
        var articleDate = entry.getPublishedDate();
        if (articleDate == null) {
            articleDate = entry.getUpdatedDate();
        }

        // Description (optional)
        String description = null;
        if (entry.getDescription() != null) {
            description = entry.getDescription().getValue();
        }

        return RssItem.builder()
                .guid(guid)
                .categoryName(categoryName)
                .articleDate(articleDate)
                .title(entry.getTitle())
                .description(description)
                .articleLink(entry.getLink())
                .imageLink(imageLink)
                .build();
    }


    private String extractImageLink(SyndEntry entry) {
        // 1) Try classic RSS <enclosure> (Postimees, etc.)
        if (entry.getEnclosures() != null && !entry.getEnclosures().isEmpty()) {
            if (entry.getEnclosures().get(0) != null &&
                    entry.getEnclosures().get(0).getUrl() != null &&
                    !entry.getEnclosures().get(0).getUrl().isBlank()) {
                return entry.getEnclosures().get(0).getUrl();
            }
        }

        // 2) Try Media RSS <media:thumbnail> (ERR)
        Module mediaModuleRaw = entry.getModule(MediaModule.URI);
        if (mediaModuleRaw instanceof MediaEntryModule mediaModule) {
            if (mediaModule.getMetadata() != null) {
                Thumbnail[] thumbnails = mediaModule.getMetadata().getThumbnail();
                if (thumbnails != null && thumbnails.length > 0 && thumbnails[0] != null) {
                    if (thumbnails[0].getUrl() != null) {
                        return thumbnails[0].getUrl().toString();
                    }
                }
            }
        }

        // 3) Nothing found
        return "";
    }

}
