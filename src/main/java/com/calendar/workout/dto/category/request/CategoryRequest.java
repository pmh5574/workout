package com.calendar.workout.dto.category.request;

import com.calendar.workout.domain.CategoryStatus;
import com.calendar.workout.domain.category.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryRequest {

    @Builder.Default
    private Long parentId = null;

    @Builder.Default
    private Integer depth = 1;

    @NotBlank(message = "카테고리 이름을 입력해 주세요.")
    private String name;

    @Builder.Default
    private CategoryStatus categoryStatus = CategoryStatus.USE;

    public Category toEntity() {
        return Category.builder()
//                .parentId(parentId)
                .depth(depth)
                .name(name)
                .categoryStatus(categoryStatus)
                .build();
    }
}
