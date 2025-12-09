package ee.valiit.rssfeedback.controller.article;

import ee.valiit.rssfeedback.controller.article.dto.ArticleFeedInfo;
import ee.valiit.rssfeedback.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/articles")
    public List<ArticleFeedInfo> getArticles(@RequestParam Integer userId, @RequestParam List<Integer> categoryIds) {
        List<ArticleFeedInfo> articles = articleService.getArticles(userId, categoryIds);
        return articles;
    }

}
