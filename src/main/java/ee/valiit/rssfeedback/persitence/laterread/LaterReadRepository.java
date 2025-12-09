package ee.valiit.rssfeedback.persitence.laterread;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LaterReadRepository extends JpaRepository<LaterRead, Integer> {


    @Query("select (count(l) > 0) from LaterRead l where l.user.id = :userId and l.article.id = :articleId")
    boolean articleExistsInLaterReadBy(Integer userId, Integer articleId);

}