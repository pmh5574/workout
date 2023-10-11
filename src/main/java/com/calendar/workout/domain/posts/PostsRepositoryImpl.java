package com.calendar.workout.domain.posts;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.calendar.workout.domain.posts.QPosts.posts;

@RequiredArgsConstructor
public class PostsRepositoryImpl implements PostsRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Posts> getList() {
        return jpaQueryFactory.selectFrom(posts)
                .orderBy(posts.id.desc())
                .fetch();
    }
}
