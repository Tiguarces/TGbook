package pl.Tiguarces.TGbook.model.user.template;

import pl.Tiguarces.TGbook.dto.user.UserDTO;
import pl.Tiguarces.TGbook.model.user.request.LoginRequest;
import pl.Tiguarces.TGbook.model.user.request.LogoutRequest;
import pl.Tiguarces.TGbook.model.user.request.RegisterUserRequest;
import pl.Tiguarces.TGbook.model.user.request.UpdateUserRequest;

import java.util.List;

public interface IUserTemplate  {
    Object register(RegisterUserRequest request);
    Object update(UpdateUserRequest request);
    void delete(String username);
    void deleteAll();
    Object login(LoginRequest request);
    void logout(LogoutRequest request);
    Object activateAccount(String nickname, String token);

    UserDTO fetchByNickname(String nickname);
    List<UserDTO> fetchAll();
}
