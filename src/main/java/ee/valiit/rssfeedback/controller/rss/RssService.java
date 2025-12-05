package ee.valiit.rssfeedback.controller.rss;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
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
import org.springframework.scheduling.annotation.Scheduled;
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


 //   @Scheduled(cron = "0 */15 * * * *")
    public void importAllArticles() {
        for (Portal portal : portalRepository.findAll()) {
            importPortalArticles(portal);
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
            Optional<PortalCategory> optionalPortalCategory = portalCategoryRepository.findPortalCategoryBy(portal, rssItem.getCategoryName());


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
            // First, let's check what content type we're getting
            log.info("Attempting to fetch RSS feed from: {}", feedUrl);

            // Validate the content before parsing
            String contentType = getContentType(feedUrl);
            log.info("Content-Type: {}", contentType);

            if (contentType != null && contentType.toLowerCase().contains("text/html")) {
                log.error("URL returns HTML content, not RSS/XML. Content-Type: {}", contentType);
                throw new RuntimeException("The provided URL returns HTML content instead of RSS feed. Please check the URL.");
            }

            // Parse the RSS feed
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(new URL(feedUrl)));

            // Log feed info to console
            log.info("=== RSS FEED INFO ===");
            log.info("Title: {}", feed.getTitle());
            log.info("Description: {}", feed.getDescription());
            log.info("Link: {}", feed.getLink());
            log.info("Published Date: {}", feed.getPublishedDate());
            log.info("Number of entries: {}", feed.getEntries().size());
            log.info("");


//            List<SyndCategory> categories = feed.getEntries().get(0).getCategories();
//
//            for (SyndCategory category : categories) {
//                String name = category.getName();
//                String label = category.getLabel();
//                String taxonomyUri = category.getTaxonomyUri();
//
//                System.out.println(name);
//            }


            // Convert entries to our DTO and log each one
            List<RssItem> rssItems = feed.getEntries().stream()
                    .limit(10) // Limit to first 10 items for console display
                    .map(this::convertToRssItem)
                    .collect(Collectors.toList());

            // Display entries in console
            log.info("=== RECENT ARTICLES ===");
            rssItems.forEach(item -> {
                log.info("Title: {}", item.getTitle());
                log.info("Description: {}",
                        item.getDescription() != null && item.getDescription().length() > 100
                                ? item.getDescription().substring(0, 100) + "..."
                                : item.getDescription());
                log.info("---");
            });

            return rssItems;

        } catch (MalformedURLException e) {
            return new ArrayList<RssItem>();
        } catch (Exception e) {
            log.error("Error fetching RSS feed from {}: {}", feedUrl, e.getMessage());

            // Give more specific error messages
            if (e.getMessage().contains("SAXParseException") || e.getMessage().contains("defer")) {
                throw new RuntimeException("The URL appears to return HTML content instead of a valid RSS feed. Please verify the RSS feed URL.");
            } else if (e.getMessage().contains("UnknownHostException")) {
                throw new RuntimeException("Cannot connect to the host. Please check the URL and your internet connection.");
            } else if (e.getMessage().contains("FileNotFoundException")) {
                throw new RuntimeException("RSS feed not found. Please verify the URL is correct.");
            }

            throw new RuntimeException("Failed to fetch RSS feed: " + e.getMessage());
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

    // Method to peek at the actual content (for debugging)
    public String peekContent(String feedUrl) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(feedUrl).openConnection();
            connection.setRequestProperty("User-Agent", "RSS-Reader/1.0");

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {

                StringBuilder content = new StringBuilder();
                String line;
                int lineCount = 0;

                // Read first 20 lines to see what we're getting
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
        return RssItem.builder()
                .guid(entry.getUri())
                .categoryName(entry.getCategories().getFirst().getName())
                .articleDate(entry.getPublishedDate())
                .title(entry.getTitle())
                .description(entry.getDescription() != null ? entry.getDescription().getValue() : null)
                .articleLink(entry.getLink())
                .imageLink(entry.getEnclosures().getFirst().getUrl())
                .build();
    }


}