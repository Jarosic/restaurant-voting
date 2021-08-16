package com.myproject.restaurantvoting.controller.user;

import com.myproject.restaurantvoting.model.Role;
import com.myproject.restaurantvoting.model.User;
import com.myproject.restaurantvoting.security.SecurityUser;
import com.myproject.restaurantvoting.service.UserService;
import com.myproject.restaurantvoting.util.ValidationUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;

@RestController
@AllArgsConstructor
@Slf4j
@Tag(name = "Account Controller")
@RequestMapping(value = "/api/account")
public class AccountController {

    private final UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@AuthenticationPrincipal SecurityUser authUser) {
        log.info("GET get auth user: {}", authUser);
        return authUser.getUser();
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<User> register(@RequestBody User user) {
        log.info("POST register user: {}", user);
        user.setRoles(Set.of(Role.USER));
        user = userService.create(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/account")
                .build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(user);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public User update(@RequestBody User user, @AuthenticationPrincipal SecurityUser authUser) {
        log.info("PUT update {} to {}", authUser, user);
        User oldUser = authUser.getUser();
        ValidationUtil.assureIdConsistent(user, oldUser.id());
        user.setRoles(oldUser.getRoles());
        return userService.update(user, authUser.id());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal SecurityUser authUser) {
        log.info("DELETE delete user {}", authUser);
        userService.delete(authUser.id());
    }
}
