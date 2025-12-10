package ee.valiit.rssfeedback.controller.feedsettings.dto;

import ee.valiit.rssfeedback.persitence.category.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link Category}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryOption implements Serializable {
    private Integer categoryId;
    private String categoryName;
    private Boolean categoryIsChosen;
}