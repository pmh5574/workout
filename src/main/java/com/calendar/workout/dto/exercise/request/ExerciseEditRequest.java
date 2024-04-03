package com.calendar.workout.dto.exercise.request;

import com.calendar.workout.domain.exercise.ExerciseType;
import com.calendar.workout.domain.exercise.ToolType;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;


@Getter
public class ExerciseEditRequest {

    @NotBlank(message = "이름을 입력해 주세요.")
    private String name;

    @NotBlank(message = "운동 횟수 방식을 선택해 주세요.")
    private ExerciseType exerciseType;

    @NotBlank(message = "운동 기구 방식을 선택해 주세요.")
    private ToolType toolType;

    private List<Long> categories = new ArrayList<>();

    @Builder
    public ExerciseEditRequest(final String name, final ExerciseType exerciseType, final ToolType toolType,
                               final List<Long> categories) {
        this.name = name;
        this.exerciseType = exerciseType;
        this.toolType = toolType;

        if (categories != null) {
            this.categories = categories;
        }
    }
}
