package com.calendar.workout.service.posts;

import com.calendar.workout.domain.member.Member;
import com.calendar.workout.domain.member.MemberRepository;
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

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long save(PostsSaveRequest postsSaveRequest) {
        Member member = memberRepository.findById(postsSaveRequest.getMemberId())
                        .orElseThrow(() -> new IllegalArgumentException("잘못된 요청입니다."));

        return postsRepository.save(postsSaveRequest.toEntity(member))
                .getId();
    }

    @Transactional(readOnly = true)
    public List<PostsListResponse> getList() {
        return postsRepository.getList().stream()
                .map(PostsListResponse::new)
                .toList();
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
