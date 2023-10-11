package com.calendar.workout.domain.posts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @BeforeEach
    void cleanUp() {
        postsRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글저장 불러오기")
    void test() {
        // given
        postsRepository.save(Posts.builder()
                .title("테스트 게시글")
                .content("테스트 본문")
                .author("test@test.com")
                .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo("테스트 게시글");
        assertThat(posts.getContent()).isEqualTo("테스트 본문");
    }

    @Test
    @DisplayName("BaseTimeEntity 등록")
    void test2() {
        // given
        LocalDateTime now = LocalDateTime.now();
        postsRepository.save(Posts.builder()
                .title("테스트 게시글")
                .content("테스트 본문")
                .author("test@test.com")
                .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);
        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }

    @Test
    @DisplayName("querydsl을 이용해서 추가한 getList 테스트")
    void test3() {
        // given
        postsRepository.save(Posts.builder()
                .title("테스트 게시글")
                .content("테스트 본문")
                .author("test@test.com")
                .build());

        postsRepository.save(Posts.builder()
                .title("테스트 게시글2")
                .content("테스트 본문2")
                .author("test@test.com2")
                .build());
        // when
        List<Posts> postsList = postsRepository.getList();

        // then
        Posts posts = postsList.get(0);
        assertThat(posts.getContent()).isEqualTo("테스트 본문2");

    }
}