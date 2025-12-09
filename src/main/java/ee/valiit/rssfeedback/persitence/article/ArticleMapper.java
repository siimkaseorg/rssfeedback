package ee.valiit.rssfeedback.persitence.article;

import ee.valiit.rssfeedback.controller.article.dto.ArticleFeedInfo;
import ee.valiit.rssfeedback.controller.rss.dto.RssItem;
import ee.valiit.rssfeedback.infrastructure.util.DateConverter;
import org.mapstruct.*;

import java.time.LocalDate;
import java.util.List;

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


//    private Integer articleId;
//    private String portalName;
//    private Integer categoryId;
//    private String categoryName;
//    private String title;
//    private String description;
//    private String articleLink;
//    private String imageLink;
//    private Boolean isInToReadList;


    @Mapping(source = "id", target = "articleId")
    @Mapping(source = "portal.name", target = "portalName")
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "articleLink", target = "articleLink")
    @Mapping(source = "imageLink", target = "imageLink")
    @Mapping(constant = "false", target = "isInReadList")
    ArticleFeedInfo toArticleFeedInfo(Article article);


   List <ArticleFeedInfo> toArticleFeedInfos(List<Article> articles);


}