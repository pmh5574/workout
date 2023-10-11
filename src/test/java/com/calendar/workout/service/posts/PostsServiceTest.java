package com.calendar.workout.service.posts;

import com.calendar.workout.domain.posts.Posts;
import com.calendar.workout.domain.posts.PostsRepository;
import com.calendar.workout.dto.posts.request.PostsSaveRequestDto;
import com.calendar.workout.dto.posts.response.PostsListResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostsServiceTest {

    @Autowired
    private PostsService postsService;

    @Autowired
    private PostsRepository postsRepository;

    @BeforeEach
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

    @Test
    @DisplayName("글 전체 조회 및 id desc 확인")
    void test2() {
        // given
        List<Posts> requestPosts = IntStream.range(1, 31)
                .mapToObj(i -> Posts.builder()
                        .author("test" + i + "@test.com")
                        .title("테스트 타이틀" + i)
                        .content("테스트" + i)
                        .build())
                .collect(Collectors.toList());
        postsRepository.saveAll(requestPosts);

        // when
        List<PostsListResponseDto> posts = postsService.getList();

        // then
        assertThat(posts).hasSize(30);
        assertThat(posts.get(0).getContent()).isEqualTo("테스트30");

    }
}