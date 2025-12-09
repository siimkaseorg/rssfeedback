package ee.valiit.rssfeedback.service;

import ee.valiit.rssfeedback.controller.article.dto.ArticleFeedInfo;
import ee.valiit.rssfeedback.persitence.article.Article;
import ee.valiit.rssfeedback.persitence.article.ArticleMapper;
import ee.valiit.rssfeedback.persitence.article.ArticleRepository;
import ee.valiit.rssfeedback.persitence.laterread.LaterReadRepository;
import ee.valiit.rssfeedback.persitence.userfeedselection.UserFeedSelection;
import ee.valiit.rssfeedback.persitence.userfeedselection.UserFeedSelectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final UserFeedSelectionRepository userFeedSelectionRepository;
    private final LaterReadRepository laterReadRepository;


    public List<ArticleFeedInfo> getArticles(Integer userId, List<Integer> categoryIds) {
        List<Article> articles = handleGetArticles(userId, categoryIds);
        List<ArticleFeedInfo> articleFeedInfos = articleMapper.toArticleFeedInfos(articles);
        handleUpdateIsInReadListInfo(userId, articleFeedInfos);
        return articleFeedInfos;
    }

    private void handleUpdateIsInReadListInfo(Integer userId, List<ArticleFeedInfo> articleFeedInfos) {
        if (isUserSpecificQuery(userId)) {
            for (ArticleFeedInfo articleFeedInfo : articleFeedInfos) {
                boolean isInReadList = laterReadRepository.articleExistsInLaterReadBy(userId, articleFeedInfo.getArticleId());
                articleFeedInfo.setIsInReadList(isInReadList);
            }
        }
    }

    private List<Article> handleGetArticles(Integer userId, List<Integer> categoryIds) {
        List<Article> articles = new ArrayList<>();
        if (isUserSpecificQuery(userId)) {
            addUserFeedSelectedArticles(userId, categoryIds, articles);
        } else  {
            articles = getAllArticlesBy(categoryIds);
        }
        return articles;
    }

    private List<Article> getAllArticlesBy(List<Integer> categoryIds) {
        List<Article> articles;
        articles = articleRepository.findArticlesBy(categoryIds);
        return articles;
    }

    private void addUserFeedSelectedArticles(Integer userId, List<Integer> categoryIds, List<Article> articles) {
        List<UserFeedSelection> userFeedSelections = userFeedSelectionRepository.findUserFeedSelectionsBy(userId, categoryIds);

        for (UserFeedSelection userFeedSelection : userFeedSelections) {
            List<Article> userArticles = articleRepository.findArticlesBy(userFeedSelection.getPortal(), userFeedSelection.getCategory(), LocalDate.now());
            articles.addAll(userArticles);
        }
    }

    private static boolean isUserSpecificQuery(Integer userId) {
        return !userId.equals(0);
    }
}

