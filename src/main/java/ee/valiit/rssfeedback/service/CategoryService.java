package ee.valiit.rssfeedback.service;

import ee.valiit.rssfeedback.controller.category.dto.CategoryInfo;
import ee.valiit.rssfeedback.persitence.category.Category;
import ee.valiit.rssfeedback.persitence.category.CategoryMapper;
import ee.valiit.rssfeedback.persitence.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryInfo> findCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryInfo> categoryInfos = categoryMapper.toCategoryInfos(categories);
        return categoryInfos;
    }

}
