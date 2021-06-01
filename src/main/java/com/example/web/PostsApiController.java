package com.example.web;

import com.example.service.posts.PostsService;
import com.example.web.dto.posts.PostsPageResponseDto;
import com.example.web.dto.posts.PostsResponseDto;
import com.example.web.dto.posts.PostsSaveRequestDto;
import com.example.web.dto.posts.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {
    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @PatchMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }

    @GetMapping("/api/v1/posts/page/{page}")
    public PostsPageResponseDto getPage(@PathVariable int page) {
        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.ASC, "id"));

        return postsService.findPage(pageable);
    }
}
