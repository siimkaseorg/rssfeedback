package ee.valiit.rssfeedback.persitence.portalcategory;

import ee.valiit.rssfeedback.persitence.category.Category;
import ee.valiit.rssfeedback.persitence.portal.Portal;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "portal_category", schema = "rss")
public class PortalCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "portal_id", nullable = false)
    private Portal portal;

    @Size(max = 255)
    @NotNull
    @Column(name = "portal_category_name", nullable = false)
    private String portalCategoryName;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

}