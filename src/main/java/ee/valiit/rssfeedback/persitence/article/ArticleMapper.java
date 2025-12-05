package ee.valiit.rssfeedback.persitence.article;

import ee.valiit.rssfeedback.controller.rss.dto.RssItem;
import ee.valiit.rssfeedback.infrastructure.util.DateConverter;
import ee.valiit.rssfeedback.persitence.category.Category;
import ee.valiit.rssfeedback.persitence.portal.Portal;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.mapstruct.*;

import java.time.LocalDate;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, imports = {DateConverter.class, LocalDate.class})
public interface ArticleMapper {



    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "articleLink", target = "articleLink")
    @Mapping(source = "imageLink", target = "imageLink")
    @Mapping(source = "guid", target = "guid")
    @Mapping(expression = "java(DateConverter.dateToLocalDate(rssItem.getArticleDate()))", target = "articleDate")
    @Mapping(constant = "A", target = "status")
    @Mapping(expression = "java(LocalDate.now())", target = "createdAt")
    Article toArticle(RssItem rssItem);


}