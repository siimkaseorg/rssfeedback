package ee.valiit.rssfeedback.service;

import ee.valiit.rssfeedback.persitence.article.Article;
import ee.valiit.rssfeedback.persitence.article.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;


    public void getArticles(Integer userId, List<Integer> categoryIds) {
        List<Article> articles = articleRepository.findArticlesBy(categoryIds);
        // todo: mappida entity List ümber DTO listiks

        // todo: kui userId ei ole '0', siis käia see DTO list läbi ükshaaval (for loop)
        // todo: Vaadata user_feed_selection tablist, cas antus userId-l on valitud categoryId olemas (exists)
    }
}
