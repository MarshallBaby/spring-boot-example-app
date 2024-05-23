package org.marshallbaby.springbootexampleapp.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.marshallbaby.springbootexampleapp.domain.User;
import org.marshallbaby.springbootexampleapp.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

// @JULA:on
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> getAllUsers(@RequestParam(defaultValue = "false", required = false) Boolean includeDisabled) {

        log.info("Getting all users. Including disabled users: [{}].", includeDisabled);
        return userService.getAllUsers(includeDisabled);
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable UUID userId) {

        log.info("Getting user by id: [{}].", userId);
        return userService.findUser(userId);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {

        log.info("Creating user with username: [{}].", user.getUsername());
        return userService.createUser(user);
    }

    @DeleteMapping("/{userId}")
    public void disableUser(@PathVariable UUID userId) {

        log.info("Disabling user: [{}].", userId);
        userService.disableUser(userId);
    }

}
