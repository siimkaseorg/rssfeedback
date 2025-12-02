package ee.valiit.rssfeedback.controller.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private Integer userId;
    private String roleName;
    private Boolean userHasSelectedFeedSettings;
}
