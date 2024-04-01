package com.calendar.workout.domain.exercise;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExerciseType {

    WEIGHT_REPETITION("무게+반복"),
    REPETITION("반복");

    private final String text;
}
