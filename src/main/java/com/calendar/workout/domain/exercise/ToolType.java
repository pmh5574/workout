package com.calendar.workout.domain.exercise;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ToolType {

    EQUIPMENT("기구"),
    BODY("맨몸");

    private final String text;
}
