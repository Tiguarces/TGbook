package pl.Tiguarces.TGbook.model.book.entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import pl.Tiguarces.TGbook.model.purchase.entity.Purchase;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @Column(unique = true, length = 149)
    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    @Length(min = 1,
            max = 149, message = "Name should contains more than 1 and smaller than 149 characters")
    private String name;

    @OneToMany(mappedBy = "book", cascade = { PERSIST, MERGE, REFRESH, REMOVE }, orphanRemoval = true)
    private List<Author> authors;

    @OneToMany(mappedBy = "book", cascade = { REFRESH, REMOVE }, orphanRemoval = true)
    private List<Review> reviews;

    @ManyToOne(cascade = { PERSIST, MERGE, REFRESH, REMOVE })
    private Publisher publisher;

    @ManyToOne(cascade = { PERSIST, MERGE, REFRESH, REMOVE })
    private SubCategory subCategory;

    @ManyToMany(mappedBy = "books")
    private List<Purchase> purchases;

    @OneToOne(mappedBy = "book", cascade = { PERSIST, MERGE, REFRESH, REMOVE }, orphanRemoval = true)
    private BookDetails details;
}
