package org.marshallbaby.springbootexampleapp.repo;

import org.marshallbaby.springbootexampleapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findUserByUsername(String username);

    List<User> findAllByActive(boolean active);

}
