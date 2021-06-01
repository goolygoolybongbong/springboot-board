package com.example.web.dto.posts;

import com.example.domain.posts.Posts;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class PostsPageResponseDto {
    private List<PostsListResponseDto> pageItems;
    private int maxPage;
    private int currentPage;

    public PostsPageResponseDto(Page<Posts> p) {
        maxPage = p.getTotalPages();
        currentPage = p.getNumber() + 1;
        pageItems = p.stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }
}
