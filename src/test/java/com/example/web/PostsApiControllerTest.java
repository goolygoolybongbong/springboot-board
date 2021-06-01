package com.example.web;

import com.example.domain.posts.Posts;
import com.example.domain.posts.PostsRepository;
import com.example.web.dto.posts.PostsResponseDto;
import com.example.web.dto.posts.PostsSaveRequestDto;
import com.example.web.dto.posts.PostsUpdateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private PostsRepository postsRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void beforeEach() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void postsRestRegistryTest() {
        // given
        String title = "test title";
        String author = "test author";
        String content = "test content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .author(author)
                .content(content)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(0L < responseEntity.getBody());
    }

    @Test
    public void postsRestRegistryTestUsingMockMvc() throws Exception {
        // given
        String title = "test title";
        String author = "test author";
        String content = "test content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .author(author)
                .content(content)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        // when, then
        String sid = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        long id = Long.parseLong(sid);
        List<Posts> p = postsRepository.findAllDesc();
        StringBuilder sb = new StringBuilder();
        assertEquals(id, p.get(0).getId());
        assertEquals(title, p.get(0).getTitle());
        assertEquals(author, p.get(0).getAuthor());
        assertEquals(content, p.get(0).getContent());
    }


    @Test
    public void postsRestRegistryRetrieveTest() throws Exception {
        // given
        String title = "test title";
        String author = "test author";
        String content = "test content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .author(author)
                .content(content)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        // when, then
        String id = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        String response = mockMvc.perform(get(url + "/" + id))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        Posts p = new ObjectMapper().readValue(response, Posts.class);
        PostsResponseDto responseDto = new PostsResponseDto(p);
        assertEquals(title, responseDto.getTitle());
        assertEquals(author, responseDto.getAuthor());
        assertEquals(content, responseDto.getContent());
    }

    @Test
    public void postsUpdateTest() throws Exception {
        // given
        String title = "test title";
        String author = "test author";
        String content = "test content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .author(author)
                .content(content)
                .build();

        String updatedTitle = title + " updated!";
        String updatedContent = content + " updated!";
        PostsUpdateRequestDto updateRequestDto = PostsUpdateRequestDto.builder()
                .title(updatedTitle)
                .content(updatedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        // when, then
        String id = mockMvc.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto))
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        String response = mockMvc.perform(
                patch(url + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateRequestDto))
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        Posts p = postsRepository.findById(Long.parseLong(response))
                .orElseThrow(() -> new IllegalArgumentException("ID: " + id + "에 해당하는 게시물이 없습니다."));
        assertEquals(updatedTitle, p.getTitle());
        assertEquals(author, p.getAuthor());
        assertEquals(updatedContent, p.getContent());
    }

    @Test
    public void postsSaveAndDeleteTest() throws Exception {
        // given
        String title = "test title";
        String author = "test author";
        String content = "test content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .author(author)
                .content(content)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        // when, then
        String id = mockMvc.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto))
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        mockMvc.perform(delete(url + "/" + id))
                .andExpect(status().isOk())
                .andDo(print());

        assertThrows(IllegalArgumentException.class, () -> postsRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new IllegalArgumentException("ID: " + id + "에 해당하는 게시물이 없습니다.")));
    }
}
