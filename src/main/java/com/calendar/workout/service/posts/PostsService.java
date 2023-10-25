package com.calendar.workout.service.posts;

import com.calendar.workout.domain.posts.Posts;
import com.calendar.workout.domain.posts.PostsRepository;
import com.calendar.workout.dto.posts.PostsEditor;
import com.calendar.workout.dto.posts.request.PostsEditRequest;
import com.calendar.workout.dto.posts.request.PostsSaveRequest;
import com.calendar.workout.dto.posts.response.PostsListResponse;
import com.calendar.workout.exception.PostsNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequest postsSaveRequest) {
        return postsRepository.save(postsSaveRequest.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    public List<PostsListResponse> getList() {
        return postsRepository.getList().stream()
                .map(PostsListResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void edit(Long postsId, PostsEditRequest postsEditRequest) {
        Posts posts = postsRepository.findById(postsId)
                .orElseThrow(PostsNotFound::new);

        PostsEditor.PostsEditorBuilder postsEditorBuilder = posts.toEditor();

        PostsEditor postsEditor = postsEditorBuilder.title(postsEditRequest.getTitle())
                        .content(postsEditRequest.getContent())
                        .build();

        posts.edit(postsEditor);
    }

    @Transactional
    public void delete(Long postsId) {
        Posts posts = postsRepository.findById(postsId)
                .orElseThrow(PostsNotFound::new);

        postsRepository.delete(posts);
    }
}
