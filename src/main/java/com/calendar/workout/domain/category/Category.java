package com.calendar.workout.domain.category;

import com.calendar.workout.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long parentId;

    @Column(nullable = false)
    private Integer depth;

    @Column(nullable = false)
    private String name;

    @Builder
    public Category(Long parentId, Integer depth, String name) {
        this.parentId = parentId;
        this.depth = depth;
        this.name = name;
    }
}
