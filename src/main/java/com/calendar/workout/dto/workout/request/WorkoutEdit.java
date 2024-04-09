package com.calendar.workout.dto.workout.request;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;

public record WorkoutEdit(Integer reps, Integer weights, LocalTime restTime, LocalDate workoutDate, String memo,
                          Long memId, Long exerciseId) {

    @Builder
    public WorkoutEdit(final Integer reps, final Integer weights, final LocalTime restTime, final LocalDate workoutDate,
                       final String memo, final Long memId, final Long exerciseId) {
        this.reps = reps;
        this.weights = weights;
        this.restTime = restTime;
        this.workoutDate = workoutDate;
        this.memo = memo;
        this.memId = memId;
        this.exerciseId = exerciseId;
    }
}
