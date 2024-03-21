package com.calendar.workout.dto.category.request;

import com.calendar.workout.domain.CategoryStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CategoryEdit {

    private Long parentId = null;

    private Integer depth = null;

    @NotBlank(message = "카테고리 이름을 입력해 주세요.")
    private String name;

    private CategoryStatus categoryStatus = null;

    @Builder
    public CategoryEdit(Long parentId, Integer depth, String name, CategoryStatus categoryStatus) {
        this.parentId = parentId;
        this.depth = depth;
        this.name = name;
        this.categoryStatus = categoryStatus;
    }
}
