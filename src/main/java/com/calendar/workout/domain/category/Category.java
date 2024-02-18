package com.calendar.workout.domain.category;

import com.calendar.workout.domain.BaseTimeEntity;
import com.calendar.workout.domain.CategoryStatus;
import com.calendar.workout.domain.exercise.CategoryExercise;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false)
    private Integer depth;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryStatus categoryStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    @OneToMany(mappedBy = "exercise")
    private List<CategoryExercise> exercises = new ArrayList<>();

    @Builder
    public Category(Category parent, Integer depth, String name) {
        this.parent = parent;
        this.depth = depth;
        this.name = name;
    }
}
