package ee.valiit.rssfeedback.persitence.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {


    @Query("select c from Category c order by c.name")
    List<Category> findCategories();
}