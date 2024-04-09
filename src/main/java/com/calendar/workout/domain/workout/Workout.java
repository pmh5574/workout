package com.calendar.workout.domain.workout;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

import com.calendar.workout.domain.BaseTimeEntity;
import com.calendar.workout.domain.exercise.Exercise;
import com.calendar.workout.domain.member.Member;
import com.calendar.workout.dto.workout.request.WorkoutEdit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Workout extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workout_id")
    private Long id;

    @Column(nullable = false)
    private Integer reps;

    @Column(nullable = false)
    private Integer weights;

    @Column(nullable = false)
    private LocalTime restTime;

    @Column(nullable = false)
    private LocalDate workoutDate;

    private String memo;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Workout(final Integer reps, final Integer weights, final LocalTime restTime, final LocalDate workoutDate,
                   final String memo) {
        this.reps = reps;
        this.weights = weights;
        this.restTime = restTime;
        this.workoutDate = workoutDate;
        this.memo = memo;

    }

    public void edit(final WorkoutEdit workoutEdit) {
        this.reps = workoutEdit.reps();
        this.weights = workoutEdit.weights();
        this.restTime = workoutEdit.restTime();
        this.workoutDate = workoutEdit.workoutDate();
        this.memo = workoutEdit.memo();
    }

    public void addExercise(final Exercise exercise) {
        this.exercise = exercise;
    }

    public void addMember(final Member member) {
        this.member = member;
    }
}
