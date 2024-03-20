package com.calendar.workout.service.category;

import com.calendar.workout.domain.category.Category;
import com.calendar.workout.domain.category.CategoryRepository;
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

        if (categoryRequest.getParentId() != null) {
            Category parentCategory = categoryRepository.findById(categoryRequest.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 카테고리를 찾을 수 없습니다."));

            category.addParentCategory(parentCategory);
        }

        return categoryRepository.save(category).getId();
    }
}
