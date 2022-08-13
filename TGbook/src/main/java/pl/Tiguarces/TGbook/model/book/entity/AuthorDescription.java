package pl.Tiguarces.TGbook.model.book.entity;

import lombok.*;

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
public class AuthorDescription {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @Lob
    @NotNull(message = "Content cannot be null")
    @NotBlank(message = "Content cannot be blank")
    private String content;

    @OneToOne(mappedBy = "description", orphanRemoval = true, cascade = { MERGE, PERSIST, REFRESH, REMOVE })
    private Author author;
}
