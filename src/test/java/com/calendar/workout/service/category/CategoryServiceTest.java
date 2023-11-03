package com.calendar.workout.service.category;


import static org.assertj.core.api.Assertions.assertThat;

import com.calendar.workout.domain.category.Category;
import com.calendar.workout.domain.category.CategoryRepository;
import com.calendar.workout.dto.category.request.CategoryRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void test() {
        // given
        CategoryRequest categoryRequest = CategoryRequest.builder()
                .name("test")
                .build();

        // when
        categoryService.save(categoryRequest);

        // then
        Category category = categoryRepository.findAll().get(0);
        assertThat(category.getName()).isEqualTo(categoryRequest.getName());

    }

}