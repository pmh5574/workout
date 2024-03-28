package com.calendar.workout.dto.category.request;

import com.calendar.workout.domain.CategoryStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CategoryEdit {

    private Long parentId = null;

    private Integer depth = 1;

    @NotBlank(message = "카테고리 이름을 입력해 주세요.")
    private String name;

    private CategoryStatus categoryStatus = null;

    @Builder
    public CategoryEdit(Long parentId, Integer depth, String name, CategoryStatus categoryStatus) {
        if (parentId != null) {
            this.parentId = parentId;
        }

        if (depth != null) {
            this.depth = depth;
        }

        this.name = name;

        if (categoryStatus != null) {
            this.categoryStatus = categoryStatus;
        }

    }
}
