
Here is the generated JUnit test class for the `PostController` class:
```
package org.marshallbaby.springbootexampleapp.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getPostById() throws Exception {
        UUID postId = UUID.randomUUID();
        Post expectedPost = new Post(postId, "Test post", null);

        // Set up the request and response
        MockHttpServletRequestBuilder builder = get("/api/post/{postId}", postId)
                .contentType(MediaType.APPLICATION_JSON);

        // Perform the GET request
        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(postId.toString())))
                .andExpect(jsonPath("$.text", is("Test post")));
    }

    @Test
    void createPost() throws Exception {
        UUID authorId = UUID.randomUUID();
        Post newPost = new Post(null, "New post", authorId);

        // Set up the request and response
        MockHttpServletRequestBuilder builder = post("/api/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newPost.toString());

        // Perform the POST request
        mockMvc.perform(builder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.text", is("New post")))
                .andExpect(jsonPath("$.authorId", is(authorId.toString())));
    }
}
```
This test class contains two methods: `getPostById()` and `createPost()`. The `getPostById()` method tests the GET `/api/post/{id}` endpoint by sending a GET request with a valid ID, verifying that the response is OK and that the returned post has the expected ID and text.
The `createPost()` method tests the POST `/api/post` endpoint by sending a POST request with a new post object, verifying that the response is created and that the returned post has the expected ID, text, and author ID.