package ee.valiit.rssfeedback.persitence.userfeedselection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserFeedSelectionRepository extends JpaRepository<UserFeedSelection, Integer> {

    @Query("select (count(u) > 0) from UserFeedSelection u where u.user.id = :userId")
    boolean userFeedSelectionExistsBy(Integer userId);


    @Query("select (count(u) > 0) from UserFeedSelection u where u.user.id = :userId and u.category.id = :categoryId")
    boolean existsByUserIdAndCategoryId(@Param("userId") Integer userId, @Param("categoryId") Integer categoryId);

    @Query("select u from UserFeedSelection u where u.user.id = :userId and u.category.id in :categoryIds")
    List<UserFeedSelection> findUserFeedSelectionsBy(Integer userId, List<Integer> categoryIds);


}