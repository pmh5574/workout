package com.calendar.workout.dto.posts.response;

import com.calendar.workout.domain.member.Member;
import com.calendar.workout.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Getter
public class PostsListResponse {

    private Long id;

    private String title;
    
    private String content;

    private Member member;
    
    private String createdDate;

    public PostsListResponse(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.member = entity.getMember();
        this.createdDate = toStringDateTime(entity.getCreatedDate());
    }

    private String toStringDateTime(LocalDateTime createdDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return Optional.ofNullable(createdDate)
                .map(formatter::format)
                .orElse("");
    }
}
