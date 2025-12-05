package ee.valiit.rssfeedback.controller.rss.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class RssItem {

    @NotNull
    @Size(max = 500)
    private String guid;

    @NotNull
    private String categoryName;

    @NotNull
    @Size(max = 500)
    private Date articleDate;

    @NotNull
    @Size(max = 255)
    private String title;

    @NotNull
    @Size(max = 1000)
    private String description;

    @NotNull
    @Size(max = 255)
    private String articleLink;

    @NotNull
    @Size(max = 255)
    private String imageLink;

}
