package com.calendar.workout.dto.exercise.request;

import com.calendar.workout.domain.category.Category;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExerciseSaveRequest {

    private String name;

    private List<Category> categories;

    @Builder
    public ExerciseSaveRequest(String name, List<Category> categories) {
        this.name = name;
        this.categories = categories;
    }
}
