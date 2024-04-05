package com.calendar.workout.dto.category.response;

import com.calendar.workout.domain.category.Category;
import com.calendar.workout.domain.category.CategoryStatus;

public record CategoryResponse(Long id, String name, CategoryStatus categoryStatus) {
    public CategoryResponse(Category entity) {
        this(entity.getId(), entity.getName(), entity.getCategoryStatus());
    }
}
