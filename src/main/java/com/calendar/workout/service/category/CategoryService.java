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

        addParentCategory(categoryRequest, category);

        return categoryRepository.save(category).getId();
    }

    @Transactional
    public Long edit(final CategoryEdit categoryEdit, final Long category_id) {
        Category category = categoryRepository.findById(category_id)
                .orElseThrow(() -> new RuntimeException("잘못된 요청 입니다."));

        addParentCategory(categoryEdit, category);

        category.edit(categoryEdit);

        return category.getId();
    }

    private void addParentCategory(CategoryRequest categoryRequest, Category category) {
        if (categoryRequest.getParentId() != null) {
            Category parentCategory = categoryRepository.findById(categoryRequest.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 카테고리를 찾을 수 없습니다."));

            category.addParentCategory(parentCategory);

        }
    }

    private void addParentCategory(CategoryEdit categoryEdit, Category category) {
        if (categoryEdit.getParentId() != null) {
            Category parentCategory = categoryRepository.findById(categoryEdit.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 카테고리를 찾을 수 없습니다."));

            category.addParentCategory(parentCategory);

        }
    }
}
