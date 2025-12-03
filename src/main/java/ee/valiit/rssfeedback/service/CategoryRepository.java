package ee.valiit.rssfeedback.service;

import ee.valiit.rssfeedback.persitence.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}