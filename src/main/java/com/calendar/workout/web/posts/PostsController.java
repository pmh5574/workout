package com.calendar.workout.web.posts;

import com.calendar.workout.dto.posts.request.PostsEditRequest;
import com.calendar.workout.dto.posts.request.PostsSaveRequest;
import com.calendar.workout.dto.posts.response.PostsListResponse;
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
    public List<PostsListResponse> getList() {
        return postsService.getList();
    }

    @PostMapping("/posts")
    public void savePosts(@RequestBody @Validated PostsSaveRequest postsSaveRequest) {
        postsService.save(postsSaveRequest);
    }

    @PatchMapping("/posts/{postsId}")
    public void editPosts(@PathVariable Long postsId, @RequestBody @Validated PostsEditRequest postsEditRequest) {
        postsService.edit(postsId, postsEditRequest);
    }

    @DeleteMapping("/posts/{postsId}")
    public void deletePosts(@PathVariable Long postsId) {
        postsService.delete(postsId);
    }
}
