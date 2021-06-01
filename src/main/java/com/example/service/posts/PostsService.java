package com.example.service.posts;

import com.example.domain.posts.Posts;
import com.example.domain.posts.PostsRepository;
import com.example.web.dto.posts.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID: " + id + "에 해당하는 게시물이 없습니다."));
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID: " + id + "에 해당하는 게시물이 없습니다."));

        return new PostsResponseDto(posts);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostsPageResponseDto findPage(Pageable pageable) {
        Page<Posts> p = postsRepository.findAll(pageable);
        if(p.getNumberOfElements() == 0){
            throw new IllegalArgumentException(p.getNumber() + "번 페이지가 존재하지 않습니다.");
        }
        return new PostsPageResponseDto(p);
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID: " + id + "에 해당하는 게시물이 없습니다."));

        postsRepository.delete(posts);
    }
}
