package pl.Tiguarces.TGbook.model.book.entity;

import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static javax.persistence.CascadeType.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @URL
    @Column(unique = true, length = 1024)
    @NotNull(message = "Url cannot be null")
    @NotBlank(message = "Url cannot be blank")
    private String url;

    @Column(columnDefinition = "TINYINT(1)", length = 1)
    private boolean main;

    @OneToOne(cascade = { MERGE, PERSIST, REFRESH, REMOVE }, orphanRemoval = true)
    private Book book;
}
