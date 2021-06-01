package com.example.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostsRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    @AfterEach
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void postSaveAndFetchTest() {
        // given
        String title = "Test title";
        String content = "Test content";
        String author = "Test author";

        postsRepository.save(Posts.builder()
                .title(title)
                .author(author)
                .content(content)
                .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);

        assertEquals(title, posts.getTitle(), "제목 비교");
        assertEquals(author, posts.getAuthor(), "작성자 비교");
        assertEquals(content, posts.getContent(), "내용 비교");
    }

    @Test
    public void baseTimeEntityRegistryTest() {
        // given
        String title = "Test title";
        String content = "Test content";
        String author = "Test author";
        LocalDateTime now = LocalDateTime.now();

        postsRepository.save(Posts.builder()
                .title(title)
                .author(author)
                .content(content)
                .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);

        assertTrue(posts.getCreatedDate().isAfter(now));
        assertTrue(posts.getModifiedDate().isAfter(now));
    }

    @Test
    public void postPagingTest() {
        // given
        ArrayList<Posts> postsArr = new ArrayList<>();
        for (int i = 0; i < 110; i++) {
            String title = "tt" + i;
            String content = "tc" + i;
            String author = "ta" + i;
            postsArr.add(Posts.builder()
                    .title(title)
                    .content(content)
                    .author(author)
                    .build());
        }
        postsRepository.saveAll(postsArr);

        // when
        Pageable thirdPageWithTenEle = PageRequest.of(2, 10);
        Pageable thirdPageWithTenEleDesc = PageRequest.of(2, 10, Sort.by(Sort.Direction.DESC, "id"));
        Page<Posts> p = postsRepository.findAll(thirdPageWithTenEle);
        Page<Posts> pd = postsRepository.findAll(thirdPageWithTenEleDesc);

        // then
        assertEquals(11, p.getTotalPages());
        for (int i = 0; i < 10; i++) {
            Posts posts = p.getContent().get(i);
            Posts postsDesc = pd.getContent().get(i);
            //assertEquals(21 + i, posts.getId(), "ID가 일치하는지 테스트"); 테스트 순서가 달라지면 ID의 시작은 달라진다.
            assertEquals("tt" + (20 + i), posts.getTitle(), "제목 검사 테스트");
            assertEquals("tt" + (89 - i), postsDesc.getTitle(), "제목 검사 내림차순 테스트");
        }
    }


    @Test
    public void pageLimitExceedTest() {
        // given
        postsRepository.save(Posts.builder()
                .title("제목")
                .content("내용")
                .author("작성자")
                .build());

        // when
        Page<Posts> p = postsRepository
                .findAll(PageRequest.of(2, 10));

        // then
        assertEquals(1, p.getTotalElements());
    }
}