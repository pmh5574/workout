package com.calendar.workout.domain.posts;

import static org.assertj.core.api.Assertions.assertThat;

import com.calendar.workout.domain.member.Member;
import com.calendar.workout.domain.member.MemberRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @Autowired
    MemberRepository memberRepository;
    Member member;


    @BeforeEach
    void cleanUp() {
        member = Member.builder()
                .oauthId("test1")
                .name("테스터")
                .email("test@test.com")
                .build();
        memberRepository.save(member);

        postsRepository.deleteAll();
    }

    @Transactional
    @Test
    @DisplayName("게시글저장 불러오기")
    void test() {
        // given
        postsRepository.save(Posts.builder()
                .title("테스트 게시글")
                .content("테스트 본문")
                .member(member)
                .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo("테스트 게시글");
        assertThat(posts.getContent()).isEqualTo("테스트 본문");
    }

    @Transactional
    @Test
    @DisplayName("BaseTimeEntity 등록")
    void test2() {
        // given
        LocalDateTime now = LocalDateTime.now();
        postsRepository.save(Posts.builder()
                .title("테스트 게시글")
                .content("테스트 본문")
                .member(member)
                .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);
        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }

    @Transactional
    @Test
    @DisplayName("querydsl을 이용해서 추가한 getList 테스트")
    void test3() {
        // given
        postsRepository.save(Posts.builder()
                .title("테스트 게시글")
                .content("테스트 본문")
                .member(member)
                .build());

        postsRepository.save(Posts.builder()
                .title("테스트 게시글2")
                .content("테스트 본문2")
                .member(member)
                .build());
        // when
        List<Posts> postsList = postsRepository.getList();

        // then
        Posts posts = postsList.get(0);
        assertThat(posts.getContent()).isEqualTo("테스트 본문2");

    }
}