package com.calendar.workout.service.posts;

import com.calendar.workout.domain.posts.PostsRepository;
import com.calendar.workout.dto.posts.request.PostsSaveRequestDto;
import com.calendar.workout.dto.posts.response.PostsListResponseDto;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
