package ee.valiit.rssfeedback.controller.rss;


import ee.valiit.rssfeedback.controller.rss.dto.RssItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/rss")
@RequiredArgsConstructor
public class RssController {

    private final RssService rssService;

    @GetMapping("/fetch")
    public ResponseEntity<List<RssItem>> fetchRssFeed(
            @RequestParam(defaultValue = "https://www.postimees.ee/rss") String feedUrl) {

        log.info("Fetching RSS feed from: {}", feedUrl);
        List<RssItem> rssItems = rssService.fetchRssFeed(feedUrl);

        return ResponseEntity.ok(rssItems);
    }


    @PostMapping("/import/portal/articles")
    public void importPortalArticles(Integer portalId) {
        rssService.importPortalArticles(portalId);
    }


    @PostMapping("/import/all/articles")
    public void importAllArticles() {
        rssService.importAllArticles();
    }


}