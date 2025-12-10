package ee.valiit.rssfeedback.controller.feedsettings.dto;

import ee.valiit.rssfeedback.controller.category.dto.CategoryInfo;
import ee.valiit.rssfeedback.persitence.category.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link Category}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortalOption implements Serializable {
    private Integer portalId;
    private String portalName;
    private Boolean portalIsChosen;
    private List<CategoryOption> categoryOptions;
}