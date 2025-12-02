package ee.valiit.rssfeedback.service;

import ee.valiit.rssfeedback.persitence.category.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void findCategories() {
        List<Category> categories = categoryRepository.findAll();

    }

}
