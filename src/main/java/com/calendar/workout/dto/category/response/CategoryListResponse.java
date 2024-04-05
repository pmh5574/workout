package com.calendar.workout.dto.category.response;

import com.calendar.workout.domain.category.Category;
import java.util.List;

public record CategoryListResponse(Long id, Integer depth, String name, Category parent, List<Category> child) {
    public CategoryListResponse(Category category) {
        this(category.getId(), category.getDepth(), category.getName(), category.getParent(), category.getChild());
    }
}
