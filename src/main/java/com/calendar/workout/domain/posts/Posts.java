package com.calendar.workout.domain.posts;

import com.calendar.workout.domain.BaseTimeEntity;
import com.calendar.workout.domain.member.Member;
import com.calendar.workout.dto.posts.PostsEditor;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Posts extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "posts_id")
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Posts(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public void edit(PostsEditor postsEditor) {
        this.title = postsEditor.getTitle();
        this.content = postsEditor.getContent();
    }

    public PostsEditor.PostsEditorBuilder toEditor() {
        return PostsEditor.builder()
                .title(this.title)
                .content(this.content);
    }
}
