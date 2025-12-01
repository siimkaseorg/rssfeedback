package ee.valiit.rssfeedback.persitence.portal;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "portal", schema = "rss")
public class Portal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 255)
    @NotNull
    @Column(name = "xml_link", nullable = false)
    private String xmlLink;

    @Size(max = 3)
    @NotNull
    @Column(name = "status", nullable = false, length = 3)
    private String status;

}