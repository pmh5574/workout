package com.calendar.workout.web.posts;

import com.calendar.workout.dto.posts.request.PostsSaveRequestDto;
import com.calendar.workout.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostsController {

    private final PostsService postsService;

    @PostMapping("/posts")
    public void savePosts(@RequestBody @Validated PostsSaveRequestDto postsSaveRequestDto) {
        postsService.save(postsSaveRequestDto);
    }
}
