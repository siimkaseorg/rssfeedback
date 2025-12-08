package ee.valiit.rssfeedback.persitence.article;

import ee.valiit.rssfeedback.persitence.category.Category;
import ee.valiit.rssfeedback.persitence.portal.Portal;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "article", schema = "rss")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "portal_id", nullable = false)
    private Portal portal;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Size(max = 255)
    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Size(max = 1000)
    @NotNull
    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    @Size(max = 255)
    @NotNull
    @Column(name = "article_link", nullable = false)
    private String articleLink;

    @Size(max = 255)
    @NotNull
    @Column(name = "image_link", nullable = false)
    private String imageLink;

    @Size(max = 3)
    @NotNull
    @Column(name = "status", nullable = false, length = 3)
    private String status;

    @Size(max = 500)
    @NotNull
    @Column(name = "guid", nullable = false, length = 500)
    private String guid;

    @NotNull
    @Column(name = "article_date", nullable = false)
    private LocalDate articleDate;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

}