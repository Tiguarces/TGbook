package pl.Tiguarces.TGbook.model.user.entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @Column(unique = true, updatable = false)
    @NotNull(message = "Username cannot be null")
    @NotBlank(message = "Username cannot be blank")
    @Length(min = 4, max = 72, message = "Username should contains from 4 to 72 characters")
    private String username;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    @Length(min = 4, max = 72, message = "Password should contains from 4 to 72 characters")
    private String password;

    @Column(columnDefinition = "TINYINT(1)", length = 1)
    private boolean enabled;

    @Enumerated(STRING)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = { PERSIST, MERGE, REFRESH, REMOVE }, orphanRemoval = true)
    private ActivationToken token;

    @OneToOne(cascade = { PERSIST, MERGE, REFRESH, REMOVE }, orphanRemoval = true)
    private Account account;
}
