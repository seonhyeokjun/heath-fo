package com.seon.springvueproject.web;

import com.seon.springvueproject.service.posts.PostsService;
import com.seon.springvueproject.web.dto.PostsResponseDto;
import com.seon.springvueproject.web.dto.PostsSaveRequestDto;
import com.seon.springvueproject.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostsApiController {
    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto postsSaveRequestDto){
        return postsService.save(postsSaveRequestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto finById(@PathVariable Long id){
        return postsService.findById(id);
    }

    @GetMapping("/api/v1/posts/list")
    public List findAll(){
        return postsService.findAll();
    }
}
