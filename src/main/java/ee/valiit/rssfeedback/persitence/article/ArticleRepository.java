package ee.valiit.rssfeedback.persitence.article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

    @Query("select (count(a) > 0) from Article a where a.guid = :guid")
    boolean articleExistsBy(String guid);

    @Query("select a from Article a where a.category.id in :categoryIds")
    List<Article> findArticlesBy(@Param("categoryIds") List<Integer> categoryIds);

}