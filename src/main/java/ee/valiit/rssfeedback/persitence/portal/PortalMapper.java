package ee.valiit.rssfeedback.persitence.portal;

import ee.valiit.rssfeedback.controller.feedsettings.dto.PortalOption;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PortalMapper {

    @Mapping(source = "id", target = "portalId")
    @Mapping(source = "name", target = "portalName")
    @Mapping(constant = "false", target = "portalIsChosen")
    PortalOption toPortalOption(Portal portal);


   List<PortalOption> toPortalOptions(List<Portal> portals);


}