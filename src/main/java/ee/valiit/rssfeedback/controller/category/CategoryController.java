package ee.valiit.rssfeedback.controller.category;

import ee.valiit.rssfeedback.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    public void findCategories() {
        categoryService.findCategories();
    }



}
