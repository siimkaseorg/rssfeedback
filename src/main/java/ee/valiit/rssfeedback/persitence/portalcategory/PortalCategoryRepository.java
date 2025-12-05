package ee.valiit.rssfeedback.persitence.portalcategory;

import ee.valiit.rssfeedback.persitence.portal.Portal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PortalCategoryRepository extends JpaRepository<PortalCategory, Integer> {

    @Query("select p from PortalCategory p where p.portal = :portal and p.portalCategoryName = :portalCategoryName")
    Optional<PortalCategory> findPortalCategoryBy(Portal portal, String portalCategoryName);


}