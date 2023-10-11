package com.calendar.workout.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long>, PostsRepositoryCustom {
}
