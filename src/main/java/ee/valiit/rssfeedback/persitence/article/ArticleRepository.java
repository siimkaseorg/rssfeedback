package ee.valiit.rssfeedback.persitence.article;

import ee.valiit.rssfeedback.persitence.category.Category;
import ee.valiit.rssfeedback.persitence.portal.Portal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

    @Query("select (count(a) > 0) from Article a where a.guid = :guid")
    boolean articleExistsBy(String guid);

    @Query("select a from Article a where a.category.id in :categoryIds")
    List<Article> findArticlesBy(List<Integer> categoryIds);

    @Query("""
            select a from Article a
            where a.portal = :portal and a.category = :category and a.articleDate = :articleDate
            order by a.portal.name, a.category.name""")
    List<Article> findArticlesBy(Portal portal, Category category, LocalDate articleDate);


}