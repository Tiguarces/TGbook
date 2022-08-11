package pl.Tiguarces.TGbook.model.book.entity;

import lombok.*;
import pl.Tiguarces.TGbook.model.user.entity.Account;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static javax.persistence.CascadeType.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @Lob
    @NotNull(message = "Content cannot be null")
    @NotBlank(message = "Content cannot be blank")
    private String content;

    @NotNull(message = "Created date cannot be null")
    @NotBlank(message = "Created date cannot be blank")
    @Pattern.List({
            @Pattern(regexp = "0[1-9]-0[1-9]-[1-2]\\d{3}", message = "Required pattern: dd-mm-yyyy"),
            @Pattern(regexp = "0[1-9]-1[1-2]-[1-2]\\d{3}", message = "Required pattern: dd-mm-yyyy"),
            @Pattern(regexp = "[1-2]\\d-0[1-9]-[1-2]\\d{3}", message = "Required pattern: dd-mm-yyyy"),
            @Pattern(regexp = "[1-2]\\d-1[1-2]-[1-2]\\d{3}", message = "Required pattern: dd-mm-yyyy"),

            @Pattern(regexp = "3[0-1]-0[1-9]-[1-2]\\d{3}", message = "Required pattern: dd-mm-yyyy"),
            @Pattern(regexp = "3[0-1]-1[1-2]-[1-2]\\d{3}", message = "Required pattern: dd-mm-yyyy")
    }) private String createdDate;

    @OneToOne(cascade = { PERSIST, MERGE, REFRESH, REMOVE }, orphanRemoval = true)
    private Account author;

    @ManyToOne(cascade = { REFRESH, REMOVE})
    private Book book;
}
