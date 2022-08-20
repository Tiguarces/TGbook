package pl.Tiguarces.TGbook.model.book.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookDetails {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @Enumerated(STRING)
    private BookType type;

    @Pattern(regexp = "(([1-9]\\d)|(0[1-9])) x (([1-9]\\d)|(0[1-9])) x (([1-9]\\d)|(0[1-9]))cm")
    private String dimensions;

    @Min(value = 1, message = "Number of pages should be greater than 1")
    private int numberOfPages;

    @Min(value = 1, message = "Amount should be greater than 1")
    private int amount;

    @NotNull(message = "Release date cannot be null")
    @NotBlank(message = "Release date cannot be blank")
    @Pattern(regexp = "((0[1-9]-0[1-9]-[1-2]\\d{3})|(3[0-1]-1[1-2]-[1-2]\\d{3})|(0[1-9]-1[1-2]-[1-2]\\d{3})|" +
            "([1-2]\\d-0[1-9]-[1-2]\\d{3})|([1-2]\\d-1[1-2]-[1-2]\\d{3})|(3[0-1]-0[1-9]-[1-2]\\d{3}))",
            message = "Required pattern: dd-mm-yyyy") private String releaseDate;

    @Min(value = 1, message = "Price should be greater than 1")
    private float price;

    @OneToMany(mappedBy = "book", cascade = { MERGE, PERSIST, REFRESH, REMOVE }, orphanRemoval = true)
    private List<Image> images;

    @OneToOne(orphanRemoval = true, cascade = { MERGE, PERSIST, REFRESH, REMOVE })
    private Book book;

    @OneToOne(mappedBy = "book", cascade = { PERSIST, MERGE, REFRESH, REFRESH }, orphanRemoval = true)
    private BookDescription description;
}
