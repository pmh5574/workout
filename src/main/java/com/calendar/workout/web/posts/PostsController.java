package com.calendar.workout.web.posts;

import com.calendar.workout.domain.posts.Posts;
import com.calendar.workout.domain.posts.PostsRepository;
import com.calendar.workout.dto.posts.request.PostsSaveRequestDto;
import com.calendar.workout.service.posts.PostsService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsController {

    private final PostsService postsService;
    private final PostsRepository postsRepository;

    @PostMapping("/posts")
    public void savePosts(@RequestBody @Validated PostsSaveRequestDto postsSaveRequestDto) {
        postsService.save(postsSaveRequestDto);
    }

    @GetMapping("/posts/{postId}")
    public Posts get(@PathVariable Long postId) {
        return postsRepository.getById(postId);
    }
}
