package ee.valiit.rssfeedback.persitence.portal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PortalRepository extends JpaRepository<Portal, Integer> {

    @Query("select p from Portal p where p.status = 'A' order by p.name")
    List<Portal> findActivePortals();
}