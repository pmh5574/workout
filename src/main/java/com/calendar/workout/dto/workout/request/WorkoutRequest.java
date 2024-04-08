package com.calendar.workout.dto.workout.request;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public record WorkoutRequest(Integer reps, Integer weights, LocalTime restTime, LocalDate workoutDate, String memo) {

    @Builder
    public WorkoutRequest(final Integer reps, final Integer weights, final LocalTime restTime,
                          final LocalDate workoutDate,
                          final String memo) {
        this.reps = reps;
        this.weights = weights;
        this.restTime = restTime;
        this.workoutDate = workoutDate;
        this.memo = memo;
    }
}
