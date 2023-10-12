package com.calendar.workout.service.posts;

import com.calendar.workout.domain.posts.Posts;
import com.calendar.workout.domain.posts.PostsRepository;
import com.calendar.workout.dto.posts.request.PostsEditRequestDto;
import com.calendar.workout.dto.posts.request.PostsSaveRequestDto;
import com.calendar.workout.dto.posts.response.PostsListResponseDto;
import com.calendar.workout.exception.PostsNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Test
    @DisplayName("글 수정")
    void test3() {
        // given
        Posts posts = Posts.builder()
                .author("test@test.com")
                .content("테스트")
                .title("테스트 타이틀")
                .build();
        postsRepository.save(posts);

        PostsEditRequestDto postsEditRequestDto = PostsEditRequestDto.builder()
                .title("테스트 수정")
                .build();

        // when
        postsService.edit(posts.getId(), postsEditRequestDto);

        // then
        Posts changedPosts = postsRepository.findById(posts.getId())
                .orElseThrow(PostsNotFound::new);
        assertThat(changedPosts.getTitle()).isEqualTo("테스트 수정");
    }

    @Test
    @DisplayName("글 수정시 없는 게시글")
    void test4() {
        // given
        Posts posts = Posts.builder()
                .author("test@test.com")
                .content("테스트")
                .title("테스트 타이틀")
                .build();
        postsRepository.save(posts);

        PostsEditRequestDto postsEditRequestDto = PostsEditRequestDto.builder()
                .title("테스트 수정")
                .build();

        // then
        assertThatThrownBy(() -> postsService.edit(2L, postsEditRequestDto))
                .isInstanceOf(PostsNotFound.class);

    }

    @Test
    @DisplayName("게시글 삭제")
    void test5() {
        // given
        Posts posts = Posts.builder()
                .author("test@test.com")
                .content("테스트")
                .title("테스트 타이틀")
                .build();
        postsRepository.save(posts);

        // when
        postsService.delete(posts.getId());

        // then
        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).isEmpty();
    }

    @Test
    @DisplayName("게시글 삭제시 없는글 요청시 오류")
    void test6() {
        // given
        Posts posts = Posts.builder()
                .author("test@test.com")
                .content("테스트")
                .title("테스트 타이틀")
                .build();
        postsRepository.save(posts);

        // when


        // then
        assertThatThrownBy(() -> postsService.delete(2L))
                .isInstanceOf(PostsNotFound.class);
        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(1);
    }

}