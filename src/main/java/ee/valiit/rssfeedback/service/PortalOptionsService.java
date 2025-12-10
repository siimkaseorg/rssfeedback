package ee.valiit.rssfeedback.service;

import ee.valiit.rssfeedback.controller.feedsettings.dto.CategoryOption;
import ee.valiit.rssfeedback.controller.feedsettings.dto.PortalOption;
import ee.valiit.rssfeedback.persitence.category.Category;
import ee.valiit.rssfeedback.persitence.category.CategoryMapper;
import ee.valiit.rssfeedback.persitence.category.CategoryRepository;
import ee.valiit.rssfeedback.persitence.portal.Portal;
import ee.valiit.rssfeedback.persitence.portal.PortalMapper;
import ee.valiit.rssfeedback.persitence.portal.PortalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PortalOptionsService {


    private final PortalRepository portalRepository;
    private final CategoryRepository categoryRepository;
    private final PortalMapper portalMapper;
    private final CategoryMapper categoryMapper;

    public List<PortalOption> getAllPortalOptions() {
        List<Portal> portals = portalRepository.findActivePortals();
        List<Category> categories = categoryRepository.findCategories();

        List<PortalOption> portalOptions = portalMapper.toPortalOptions(portals);

        for (PortalOption portalOption : portalOptions) {
            List<CategoryOption> categoryOptions = categoryMapper.toCategoryOptions(categories);
            portalOption.setCategoryOptions(categoryOptions);
        }

        return portalOptions;
    }
}
