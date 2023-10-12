package com.calendar.workout.web.posts;

import com.calendar.workout.dto.posts.request.PostsEditRequestDto;
import com.calendar.workout.dto.posts.request.PostsSaveRequestDto;
import com.calendar.workout.dto.posts.response.PostsListResponseDto;
import com.calendar.workout.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostsController {

    private final PostsService postsService;

    @GetMapping("/posts")
    public List<PostsListResponseDto> getList() {
        return postsService.getList();
    }

    @PostMapping("/posts")
    public void savePosts(@RequestBody @Validated PostsSaveRequestDto postsSaveRequestDto) {
        postsService.save(postsSaveRequestDto);
    }

    @PatchMapping("/posts/{postsId}")
    public void editPosts(@PathVariable Long postsId, @RequestBody @Validated PostsEditRequestDto postsEditRequestDto) {
        postsService.edit(postsId, postsEditRequestDto);
    }

    @DeleteMapping("/posts/{postsId}")
    public void deletePosts(@PathVariable Long postsId) {
        postsService.delete(postsId);
    }
}
