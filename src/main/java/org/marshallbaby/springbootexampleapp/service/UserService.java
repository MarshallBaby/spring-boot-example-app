package org.marshallbaby.springbootexampleapp.service;

import lombok.RequiredArgsConstructor;
import org.marshallbaby.springbootexampleapp.domain.User;
import org.marshallbaby.springbootexampleapp.exception.NotFoundException;
import org.marshallbaby.springbootexampleapp.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.List;
import java.util.UUID;

import static java.lang.String.format;
import static java.time.LocalDateTime.now;

// @JULA:on
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final Clock appClock;

    public List<User> getAllUsers(boolean includeDisabled) {

        return includeDisabled ?
                userRepository.findAll() :
                userRepository.findAllByActive(true);
    }

    public User findUser(UUID userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(format("Could not find user with id: [%s].", userId)));
    }

    public User createUser(User user) {

        validateUsernameIsNotTaken(user.getUsername());

        user.setActive(true);
        user.setRegistrationTimestamp(now(appClock));

        return userRepository.save(user);
    }

    public void disableUser(UUID userId) {

        User user = findUser(userId);
        user.setActive(false);
        userRepository.save(user);
    }

    private void validateUsernameIsNotTaken(String username) {

        var user = userRepository.findUserByUsername(username);

        if (user.isPresent()) {

            String message = format("Username has been already taken: [%s].", username);
            throw new IllegalArgumentException(message);
        }
    }

}
