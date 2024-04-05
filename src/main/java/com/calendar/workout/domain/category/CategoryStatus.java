package com.calendar.workout.domain.category;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryStatus {
    USE("사용"),
    NO_USE("사용 안함"),
    DELETE("삭제");

    private final String text;
}
