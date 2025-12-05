package ee.valiit.rssfeedback.persitence.article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
    @Query("select (count(a) > 0) from Article a where a.guid = :guid")
    boolean articleExistsBy(String guid);

}