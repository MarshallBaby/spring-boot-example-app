package org.marshallbaby.springbootexampleapp.repo;

import org.marshallbaby.springbootexampleapp.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {

    List<Post> findAllByAuthorId(UUID authorId);

}
