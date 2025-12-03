package ee.valiit.rssfeedback.controller.category;

import ee.valiit.rssfeedback.controller.category.dto.CategoryInfo;
import ee.valiit.rssfeedback.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    public List<CategoryInfo> findCategories() {
        List<CategoryInfo> categories = categoryService.findCategories();
        return categories;
    }


}
