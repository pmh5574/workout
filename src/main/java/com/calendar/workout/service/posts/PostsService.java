package com.calendar.workout.service.posts;

import com.calendar.workout.domain.posts.Posts;
import com.calendar.workout.domain.posts.PostsRepository;
import com.calendar.workout.dto.posts.PostsEditor;
import com.calendar.workout.dto.posts.request.PostsEditRequestDto;
import com.calendar.workout.dto.posts.request.PostsSaveRequestDto;
import com.calendar.workout.dto.posts.response.PostsListResponseDto;
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
    public Long save(PostsSaveRequestDto postsSaveRequestDto) {
        return postsRepository.save(postsSaveRequestDto.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> getList() {
        return postsRepository.getList().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void edit(Long postsId, PostsEditRequestDto postsEditRequestDto) {
        Posts posts = postsRepository.findById(postsId)
                .orElseThrow(PostsNotFound::new);

        PostsEditor.PostsEditorBuilder postsEditorBuilder = posts.toEditor();

        PostsEditor postsEditor = postsEditorBuilder.title(postsEditRequestDto.getTitle())
                        .content(postsEditRequestDto.getContent())
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
