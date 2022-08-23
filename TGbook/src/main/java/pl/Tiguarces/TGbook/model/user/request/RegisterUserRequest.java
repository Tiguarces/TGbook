package pl.Tiguarces.TGbook.model.user.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RegisterUserRequest {

    @NotNull(message = "Username cannot be null")
    @NotBlank(message = "Username cannot be blank")
    @Length(min = 4, max = 72, message = "Username should contains from 4 to 72 characters")

    private String username;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    @Length(min = 4, max = 72, message = "Username should contains from 4 to 72 characters")

    private String password;

    @NotNull(message = "Nickname cannot be null")
    @NotBlank(message = "Nickname cannot be blank")
    @Length(min = 4, max = 72, message = "Username should contains from 4 to 72 characters")

    private String nickname;

    @Email
    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be blank")
    private String email;
}
