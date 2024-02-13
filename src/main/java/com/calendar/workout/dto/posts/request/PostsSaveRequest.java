package com.calendar.workout.dto.posts.request;

import com.calendar.workout.domain.member.Member;
import com.calendar.workout.domain.posts.Posts;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequest {

    @NotBlank(message = "제목을 입력해 주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해 주세요.")
    private String content;

    @NotBlank(message = "회원번호가 없습니다.")
    private Long memberId;

    @Builder
    public PostsSaveRequest(String title, String content, Long memberId) {
        this.title = title;
        this.content = content;
        this.memberId = memberId;
    }

    public Posts toEntity(Member member) {
        return Posts.builder()
                .title(title)
                .content(content)
                .member(member)
                .build();
    }

}
