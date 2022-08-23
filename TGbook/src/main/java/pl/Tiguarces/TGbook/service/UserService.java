package pl.Tiguarces.TGbook.service;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.Tiguarces.TGbook.dto.Mapper;
import pl.Tiguarces.TGbook.dto.user.UserDTO;
import pl.Tiguarces.TGbook.model.user.entity.Account;
import pl.Tiguarces.TGbook.model.user.entity.ActivationToken;
import pl.Tiguarces.TGbook.model.user.entity.Role;
import pl.Tiguarces.TGbook.model.user.entity.User;
import pl.Tiguarces.TGbook.model.user.repository.IAccountRepository;
import pl.Tiguarces.TGbook.model.user.repository.IActivationTokenRepository;
import pl.Tiguarces.TGbook.model.user.repository.IUserRepository;
import pl.Tiguarces.TGbook.model.user.request.LoginRequest;
import pl.Tiguarces.TGbook.model.user.request.LogoutRequest;
import pl.Tiguarces.TGbook.model.user.request.RegisterUserRequest;
import pl.Tiguarces.TGbook.model.user.request.UpdateUserRequest;
import pl.Tiguarces.TGbook.model.user.template.IUserTemplate;
import pl.Tiguarces.TGbook.security.exceptions.EntityRecordExists;
import pl.Tiguarces.TGbook.service.utils.EntityHelper;
import pl.Tiguarces.TGbook.service.utils.MailSender;
import pl.Tiguarces.TGbook.service.utils.MailType;
import pl.Tiguarces.TGbook.service.utils.TimeHelper;
import tiguarces.PasswD;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements IUserTemplate {
    private final EntityHelper helper;
    private final MailSender mailSender;
    private final Mapper mapper;
    private final TimeHelper timeHelper;
    private final IUserRepository userRepository;
    private final IAccountRepository accountRepository;
    private final IActivationTokenRepository activationRepository;

    @Override
    public Object register(final RegisterUserRequest request) {
        final String username = request.getUsername();
        final String password = request.getPassword();
        final String nickname = request.getNickname();
        final String email = request.getEmail();

        if(userRepository.existsByUsername(username))
            throw new EntityRecordExists("User", "username", username);

        if(accountRepository.existsByNickname(nickname))
            throw new EntityRecordExists("User", "nickname", nickname);

        final ActivationToken activationToken = helper.getActivationToken(username);
        final Account account = helper.getAccount(email, nickname);
        final User user = helper.getUser(Map.of(
                "username", username,
                "password", password,
                "nickname", nickname,
                "email", email
        ), account, Role.USER, activationToken);

        mailSender.sendEmail(nickname, activationToken.getToken(), "TGBook@mail.com",email, MailType.ACTIVATION);
        log.info("Email has been sent successfully");

        userRepository.save(user);
        log.info("User has been saved in the database");

        return Response.builder()
                    .date(timeHelper.getDate(Instant.now()))
                    .message("User has been created")
                .build();
    }

    @Getter
    @Setter
    @Builder
    static class Response {
        private String message;
        private String date;
    }

    @Override
    public Object update(final UpdateUserRequest request) {
        userRepository.findByUsername(request.getUsername())
                .ifPresentOrElse((foundUser) -> {
                    final String currentPassword = foundUser.getPassword();
                    final String decodedCurrentPassword = PasswD.decode(currentPassword);

                    final String newPassword = request.getPassword();
                    final String newEmail = request.getEmail();

                    boolean updated = false;
                    if(newPassword != null && !newPassword.isBlank() && !newPassword.equals(decodedCurrentPassword)) {
                        final String newEncodedPassword = PasswD.encode(newPassword);
                        foundUser.setPassword(newEncodedPassword);
                        updated = true;

                    } else if(newEmail != null && !newEmail.isBlank() && newEmail.matches("[a-zA-Z]@[a-zA-Z].[a-zA-Z]{2,3}")) {
                        foundUser.getAccount().setEmail(newEmail);
                        updated = true;
                    }

                    if(updated) {
                        userRepository.save(foundUser);
                        log.info("User has been updated");
                    } else
                        log.info("User has not been updated");

                },() -> {
                    throw new RuntimeException("User not found");
                });

        return Response.builder()
                    .message("User has been updated")
                    .date(timeHelper.getDate(Instant.now()))
                .build();
    }

    @Override
    public void delete(final String username) {
        if(userRepository.existsByUsername(username)) {
            userRepository.deleteByUsername(username);
            log.info("User {} deleted", username);
        } else {
            throw new RuntimeException("User not exists");
        }
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
        log.info("Deleted all users");
    }

    @Override
    public Object login(final LoginRequest request) {
        final String username = request.getUsername();
        final String password = request.getPassword();
        final User foundUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not exists"));

        if(!foundUser.isEnabled())
            throw new RuntimeException("User not activated");

        final String decodedPassword = foundUser.getPassword();
        if(!password.equals(decodedPassword))
            throw new RuntimeException("Password not valid");

        final UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(username, password,
                        List.of(new SimpleGrantedAuthority(foundUser.getRole().name())));

        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);
        log.info("User has been logged successfully");

        return LoginResponse.builder()
                    .encodedSession(getEncodedSession(username))
                    .date(timeHelper.getDate(Instant.now()))
                .build();
    }

    @Getter
    @Setter
    @Builder
    static class LoginResponse {
        private String encodedSession;
        private String date;
    }

    private String getEncodedSession(final String username) {
        final String session = "%s|%s|%s";
        final String date = timeHelper.getDate(Instant.now());
        final String server = "TGBook";

        log.info("Session encoded and has been send to user");
        return PasswD.encode(
                session.formatted(date, server, username));
    }

    @Override
    public void logout(final LogoutRequest request) {
        final String username = request.getUsername();
        final SecurityContext context = SecurityContextHolder.getContext();

        if(userRepository.existsByUsername(username)) {
            if (context != null) {
                final Authentication authentication = context.getAuthentication();
                boolean userIsNotAnonymous = !(authentication instanceof AnonymousAuthenticationToken);

                if(userIsNotAnonymous && authentication.isAuthenticated()) {
                    SecurityContextHolder.clearContext();
                    log.info("User {} logged out successfully", username);
                    return;
                }
            } throw new RuntimeException("User not logged");
        } else
            throw new RuntimeException("User not exists");
    }

    @Override
    @Transactional
    public Object activateAccount(final String nickname, final String token)  {
        final ActivationToken foundToken = activationRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Not found token"));

        final User foundTokenUser = foundToken.getUser();
        final Account foundTokenAccount = foundTokenUser.getAccount();

        if(!nickname.equals(foundTokenAccount.getNickname()))
            throw new RuntimeException("User is not owner the token");

        if(foundTokenUser.isEnabled())
            throw new RuntimeException("User is already activated");

        foundTokenUser.setEnabled(true);
        userRepository.save(foundTokenUser);

        activationRepository.deleteByToken(token);
        log.info("User has been activated successfully");

        return Response.builder()
                    .message("User has been activated")
                    .date(timeHelper.getDate(Instant.now()))
                .build();
    }

    @Override
    public UserDTO fetchByNickname(final String nickname) {
        final Account account = accountRepository.findByNickname(nickname)
                .orElseThrow(() -> new RuntimeException("User not found"));

        log.info("Account fetched");
        return mapper.mapToAccountDTO(account);
    }

    @Override
    public List<UserDTO> fetchAll() {
        final List<User> users = userRepository.findAll();

        log.info("Account fetched");
        return mapper.mapAllToAccountDTO(users);
    }

}
