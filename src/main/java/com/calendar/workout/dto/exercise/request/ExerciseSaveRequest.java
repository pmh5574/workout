package com.calendar.workout.dto.exercise.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExerciseSaveRequest {

    private String name;

    @Builder
    public ExerciseSaveRequest(String name) {
        this.name = name;
    }
}
