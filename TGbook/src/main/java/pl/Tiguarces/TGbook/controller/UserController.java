package pl.Tiguarces.TGbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.Tiguarces.TGbook.model.user.request.LoginRequest;
import pl.Tiguarces.TGbook.model.user.request.LogoutRequest;
import pl.Tiguarces.TGbook.model.user.request.RegisterUserRequest;
import pl.Tiguarces.TGbook.model.user.request.UpdateUserRequest;
import pl.Tiguarces.TGbook.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/auth")
public class UserController {
    private final UserService userService;

    @PostMapping(path = "/login")
    public ResponseEntity<Object> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity
                .status(200)
                .body(userService.login(request));
    }

    @PostMapping(path = "/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterUserRequest request) {
        return ResponseEntity
                .status(201)
                .body(userService.register(request));
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<?> logout(@RequestBody @Valid LogoutRequest request) {
        userService.logout(request);
        return ResponseEntity
                .status(204)
                .build();
    }

    @PutMapping(path = "/update")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Object> updateUser(@RequestBody @Valid UpdateUserRequest request) {
        return ResponseEntity
                .status(204)
                .body(userService.update(request));
    }

    @GetMapping(path = "/activate")
    public ResponseEntity<Object> activateUser(@Valid @RequestParam("nickname")
                                              @NotNull(message = "Nickname cannot be null")
                                              @NotBlank(message = "Nickname cannot be blank") String nickname,
                                          @Valid @RequestParam("token")
                                              @NotNull(message = "Nickname cannot be null")
                                              @NotBlank(message = "Nickname cannot be blank") String token) {
        return ResponseEntity
                .status(200)
                .body(userService.activateAccount(nickname, token));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/delete/by-username")
    public ResponseEntity<?> deleteByUsername(@Valid @RequestParam("username")
                                              @NotNull(message = "Username cannot be null")
                                              @NotBlank(message = "Username cannot be blank") String username) {
        userService.delete(username);
        return ResponseEntity
                .status(204)
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/delete/all")
    public ResponseEntity<?> deleteAll() {
        userService.deleteAll();
        return ResponseEntity
                .status(204)
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/fetch/all")
    public ResponseEntity<List<?>> fetchAll() {
        return ResponseEntity
                .status(200)
                .body(userService.fetchAll());
    }

    @GetMapping(path = "/fetch/by-nickname")
    public ResponseEntity<Object> fetchByNickname(@Valid @RequestParam("nickname")
                                                       @NotNull(message = "Nickname cannot be null")
                                                       @NotBlank(message = "Nickname cannot be blank") String nickname) {
        return ResponseEntity
                .status(200)
                .body(userService.fetchByNickname(nickname));
    }
}
