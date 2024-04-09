package com.calendar.workout.domain.exercise;

import static lombok.AccessLevel.PROTECTED;

import com.calendar.workout.domain.BaseTimeEntity;
import com.calendar.workout.domain.workout.Workout;
import com.calendar.workout.dto.exercise.request.ExerciseEditRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Exercise extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private ExerciseType exerciseType;

    @Enumerated(EnumType.STRING)
    private ToolType toolType;

    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL)
    private List<CategoryExercise> exerciseCategories = new ArrayList<>();

    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL)
    private List<Workout> workouts = new ArrayList<>();

    @Builder
    public Exercise(final String name, final ExerciseType exerciseType, final ToolType toolType) {
        this.name = name;
        this.exerciseType = exerciseType;
        this.toolType = toolType;
    }

    public void addCategoryExerciseList(final List<CategoryExercise> categoryExercises) {
        this.exerciseCategories = categoryExercises;
    }

    public void edit(final ExerciseEditRequest exerciseEditRequest) {
        this.name = exerciseEditRequest.getName();
        this.exerciseType = exerciseEditRequest.getExerciseType();
        this.toolType = exerciseEditRequest.getToolType();


    }

    public void removeCategoryExercise() {
        exerciseCategories.clear();
    }
}
