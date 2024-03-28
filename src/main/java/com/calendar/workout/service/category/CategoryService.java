package com.calendar.workout.service.category;

import com.calendar.workout.domain.category.Category;
import com.calendar.workout.domain.category.CategoryRepository;
import com.calendar.workout.dto.category.request.CategoryEdit;
import com.calendar.workout.dto.category.request.CategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public Long save(final CategoryRequest categoryRequest) {

        Category category = categoryRequest.toEntity();

        addParentCategory(categoryRequest.getParentId(), category);

        return categoryRepository.save(category).getId();
    }

    @Transactional
    public Long edit(final CategoryEdit categoryEdit, final Long category_id) {

        Category category = categoryRepository.findById(category_id)
                .orElseThrow(() -> new RuntimeException("잘못된 요청 입니다."));

        category.edit(categoryEdit);

        addParentCategory(categoryEdit.getParentId(), category);

        return category.getId();
    }

    private void addParentCategory(Long parentId, Category category) {
        if (parentId != null) {
            Category parentCategory = categoryRepository.findById(parentId)
                    .orElseThrow(() -> new IllegalArgumentException("부모 카테고리를 찾을 수 없습니다."));

            category.addParentCategory(parentCategory);
        }
    }

}
