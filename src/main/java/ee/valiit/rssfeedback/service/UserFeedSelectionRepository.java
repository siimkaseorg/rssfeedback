package ee.valiit.rssfeedback.service;

import ee.valiit.rssfeedback.persitence.userfeedselection.UserFeedSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserFeedSelectionRepository extends JpaRepository<UserFeedSelection, Integer> {

    @Query("select (count(u) > 0) from UserFeedSelection u where u.user.id = :userId")
    boolean userFeedSelectionExistsBy(Integer userId);


}