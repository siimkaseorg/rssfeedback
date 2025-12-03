package ee.valiit.rssfeedback.persitence.category;

import ee.valiit.rssfeedback.controller.category.dto.CategoryInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {


    @Mapping(source = "id", target = "categoryId")
    @Mapping(source = "name", target = "categoryName")
    @Mapping(constant = "true", target = "categoryIsChosen")
    CategoryInfo toCategoryInfo(Category category);


    List<CategoryInfo> toCategoryInfos(List<Category> categories);


}