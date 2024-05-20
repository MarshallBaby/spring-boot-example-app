
Here is the generated JUnit test class for the `PostController` class:
```
package org.marshallbaby.springbootexampleapp.rest;

import static org.junit.jupiter.api.Assertions.*;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPostById() throws Exception {
        UUID postId = UUID.randomUUID();
        Post expectedPost = new Post(postId, "Some text", null, LocalDateTime.now());

        when(postService.findPost(postId)).thenReturn(expectedPost);

        mockMvc.perform(MockMvcRequestBuilders.get("/posts/" + postId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getPosts() throws Exception {
        UUID authorId = UUID.randomUUID();
        List<Post> expectedPosts = Collections.singletonList(new Post(UUID.randomUUID(), "Some text", authorId, LocalDateTime.now()));

        when(postService.getPosts(authorId)).thenReturn(expectedPosts);

        mockMvc.perform(MockMvcRequestBuilders.get("/posts?authorId=" + authorId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
}
```