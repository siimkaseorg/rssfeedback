package ee.valiit.rssfeedback.controller.feedsettings;

import ee.valiit.rssfeedback.controller.feedsettings.dto.PortalOption;
import ee.valiit.rssfeedback.service.PortalOptionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PortalOptionsController {

    private final PortalOptionsService portalOptionsService;

    @GetMapping("/portal/options")
    public List<PortalOption> getAllPortalOptions() {
        return portalOptionsService.getAllPortalOptions();
    }
}
