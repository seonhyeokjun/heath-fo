package com.seon.springvueproject.service.posts;

import com.seon.springvueproject.domain.posts.Posts;
import com.seon.springvueproject.domain.posts.PostsRepository;
import com.seon.springvueproject.web.dto.PostsResponseDto;
import com.seon.springvueproject.web.dto.PostsSaveRequestDto;
import com.seon.springvueproject.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto postsSaveRequestDto) {
        return postsRepository.save(postsSaveRequestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 없습니다. ID=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    public List findAll(){
        postsRepository.findAll().forEach(mem -> {
            log.info(mem.toString());
        });
        return postsRepository.findAll();
    }
}
