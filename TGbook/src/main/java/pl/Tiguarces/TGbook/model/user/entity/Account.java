package pl.Tiguarces.TGbook.model.user.entity;

import lombok.*;
import pl.Tiguarces.TGbook.model.book.entity.Review;
import pl.Tiguarces.TGbook.model.purchase.entity.Purchase;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @Column(unique = true, updatable = false)
    @NotNull(message = "Nickname cannot be null")
    @NotBlank(message = "Nickname cannot be blank")
    private String nickname;

    @NotNull(message = "Created date cannot be null")
    @NotBlank(message = "Created date cannot be blank")
    @Pattern(regexp = "((0[1-9]-0[1-9]-[1-2]\\d{3})|(3[0-1]-1[1-2]-[1-2]\\d{3})|(0[1-9]-1[1-2]-[1-2]\\d{3})|" +
            "([1-2]\\d-0[1-9]-[1-2]\\d{3})|([1-2]\\d-1[1-2]-[1-2]\\d{3})|(3[0-1]-0[1-9]-[1-2]\\d{3}))",
            message = "Required pattern: dd-mm-yyyy") private String createdDate;

    @Email
    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @OneToMany(mappedBy = "purchaser", cascade = { REFRESH, REMOVE }, orphanRemoval = true)
    private List<Purchase> purchases;

    @OneToMany(mappedBy = "author", cascade = { REFRESH, REMOVE }, orphanRemoval = true)
    private List<Review> reviews;

    @OneToOne(mappedBy = "account", cascade = { REFRESH, REMOVE }, orphanRemoval = true)
    private User user;
}
