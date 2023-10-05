package com.calendar.workout.service.posts;

import com.calendar.workout.domain.posts.Posts;
import com.calendar.workout.domain.posts.PostsRepository;
import com.calendar.workout.dto.posts.request.PostsSaveRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostsServiceTest {

    @Autowired
    private PostsService postsService;

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    public void cleanup () {
        postsRepository.deleteAll();
    }

    @Test
    @DisplayName("Dto 데이터가 posts 테이블에 저장된다.")
    void test() {
        // given
        PostsSaveRequestDto dto = PostsSaveRequestDto.builder()
                .author("test@test.com")
                .content("테스트")
                .title("테스트 타이틀")
                .build();

        // when
        postsService.save(dto);

        // then
        Posts posts = postsRepository.findAll().get(0);
        assertThat(posts.getAuthor()).isEqualTo(dto.getAuthor());
        assertThat(posts.getContent()).isEqualTo(dto.getContent());
        assertThat(posts.getTitle()).isEqualTo(dto.getTitle());

    }
}