package com.calendar.workout.service.posts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.calendar.workout.domain.member.Member;
import com.calendar.workout.domain.member.MemberRepository;
import com.calendar.workout.domain.posts.Posts;
import com.calendar.workout.domain.posts.PostsRepository;
import com.calendar.workout.dto.posts.request.PostsEditRequest;
import com.calendar.workout.dto.posts.request.PostsSaveRequest;
import com.calendar.workout.dto.posts.response.PostsListResponse;
import com.calendar.workout.exception.PostsNotFound;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostsServiceTest {

    @Autowired
    private PostsService postsService;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    MemberRepository memberRepository;

    Member member;

    @BeforeEach
    public void cleanup() {
        member = Member.builder()
                .oauthId("test1")
                .name("테스터")
                .email("test@test.com")
                .build();
        memberRepository.save(member);

        postsRepository.deleteAll();
    }

    @Test
    @DisplayName("Dto 데이터가 posts 테이블에 저장된다.")
    void test() {
        // given
        PostsSaveRequest dto = PostsSaveRequest.builder()
                .memberId(member.getId())
                .content("테스트")
                .title("테스트 타이틀")
                .build();

        // when
        postsService.save(dto);

        // then
        Posts posts = postsRepository.findAll().get(0);
//        assertThat(posts.getAuthor()).isEqualTo(dto.getAuthor());
        assertThat(posts.getContent()).isEqualTo(dto.getContent());
        assertThat(posts.getTitle()).isEqualTo(dto.getTitle());

    }

    @Test
    @DisplayName("글 전체 조회 및 id desc 확인")
    void test2() {
        // given
        List<Posts> requestPosts = IntStream.range(1, 31)
                .mapToObj(i -> Posts.builder()
                        .member(member)
                        .title("테스트 타이틀" + i)
                        .content("테스트" + i)
                        .build())
                .collect(Collectors.toList());
        postsRepository.saveAll(requestPosts);

        // when
        List<PostsListResponse> posts = postsService.getList();

        // then
        assertThat(posts).hasSize(30);
        assertThat(posts.get(0).getContent()).isEqualTo("테스트30");

    }

    @Test
    @DisplayName("글 수정")
    void test3() {
        // given
        Posts posts = Posts.builder()
                .member(member)
                .content("테스트")
                .title("테스트 타이틀")
                .build();
        postsRepository.save(posts);

        PostsEditRequest postsEditRequest = PostsEditRequest.builder()
                .title("테스트 수정")
                .build();

        // when
        postsService.edit(posts.getId(), postsEditRequest);

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
                .member(member)
                .content("테스트")
                .title("테스트 타이틀")
                .build();
        postsRepository.save(posts);

        PostsEditRequest postsEditRequest = PostsEditRequest.builder()
                .title("테스트 수정")
                .build();

        // then
        assertThatThrownBy(() -> postsService.edit(2L, postsEditRequest))
                .isInstanceOf(PostsNotFound.class);

    }

    @Test
    @DisplayName("게시글 삭제")
    void test5() {
        // given
        Posts posts = Posts.builder()
                .member(member)
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
                .member(member)
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