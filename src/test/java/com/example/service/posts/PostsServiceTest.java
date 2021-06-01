package com.example.service.posts;

import com.example.domain.posts.PostsRepository;
import com.example.web.dto.posts.PostsPageResponseDto;
import com.example.web.dto.posts.PostsSaveRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostsServiceTest {
    @Autowired
    PostsService postsService;
    @Autowired
    PostsRepository postsRepository;

    @AfterEach
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void postSaveTest() {
        // given
        String title = "나는 제목";
        String content = "나는 내용";
        String author = "나는 작성자";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        // when
        postsService.save(requestDto);

        // then
        var list = postsRepository.findAll();
        var p = list.get(0);

        assertEquals(title, p.getTitle());
        assertEquals(content, p.getContent());
        assertEquals(author, p.getAuthor());
    }

    @Test
    public void pageExceptionTest() {
        // given
        String title = "나는 제목";
        String content = "나는 내용";
        String author = "나는 작성자";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
        postsService.save(requestDto);

        // when, then
        Pageable p = PageRequest.of(1, 10);
        Pageable finalP = p;
        assertThrows(IllegalArgumentException.class, () -> postsService.findPage(finalP), "없는 페이지를 요청하면 예외 발생");

        p = PageRequest.of(0, 10);
        PostsPageResponseDto r = postsService.findPage(p);
        assertEquals(1, r.getMaxPage(), "정상적인 페이지 요청");
    }
}
