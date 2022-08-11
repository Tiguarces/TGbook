package pl.Tiguarces.TGbook.model.book.entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;

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
public class Author {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @Column(unique = true)
    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    @Length(min = 1,
            max = 72, message = "Book name should contains more than 1 and smaller than 72 characters")
    private String name;

    @Column(unique = true)
    @NotNull(message = "Portrait image URL cannot be null")
    @NotBlank(message = "Portrait image URL cannot be blank")
    private String portraitImageURL;

    @OneToOne(cascade = { MERGE, PERSIST, REFRESH, REMOVE }, orphanRemoval = true)
    private AuthorDescription description;

    @ManyToOne(cascade = { REFRESH, REMOVE })
    private Book book;
}
