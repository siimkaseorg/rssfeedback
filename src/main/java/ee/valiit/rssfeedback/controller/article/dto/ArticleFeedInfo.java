package ee.valiit.rssfeedback.controller.article.dto;

import ee.valiit.rssfeedback.persitence.article.Article;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link Article}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleFeedInfo implements Serializable {
    private Integer articleId;
    private String portalName;
    private Integer categoryId;
    private String categoryName;
    private String title;
    private String description;
    private String articleLink;
    private String imageLink;
    private Boolean isInToReadList;
}