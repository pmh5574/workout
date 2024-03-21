package com.calendar.workout.service.category;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.calendar.workout.domain.CategoryStatus;
import com.calendar.workout.domain.category.Category;
import com.calendar.workout.domain.category.CategoryRepository;
import com.calendar.workout.dto.category.request.CategoryEdit;
import com.calendar.workout.dto.category.request.CategoryRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @AfterEach
    void cleanUp() {
        categoryRepository.deleteAll();
    }

    @Test
    @DisplayName("등록")
    void save() {
        // given
        CategoryRequest categoryRequest = CategoryRequest.builder()
                .name("test")
                .build();

        // when
        categoryService.save(categoryRequest);

        // then
        Category category = categoryRepository.findAll().get(0);
        assertThat(category.getName()).isEqualTo(categoryRequest.getName());
        assertThat(category.getCategoryStatus()).isEqualTo(categoryRequest.getCategoryStatus());
    }

    @DisplayName("부모 카테고리 등록")
    @Test
    void childSave() {
        // given
        CategoryRequest categoryRequest = CategoryRequest.builder()
                .name("test")
                .build();
        Long parentId = categoryService.save(categoryRequest);

        CategoryRequest categoryRequest2 = CategoryRequest.builder()
                .name("test")
                .depth(2)
                .parentId(parentId)
                .build();

        // when
        Long childId = categoryService.save(categoryRequest2);

        // then
        Category category = categoryRepository.findById(childId)
                .orElseThrow(() -> new IllegalArgumentException("잘못됨"));

        assertThat(parentId).isEqualTo(category.getParent().getId());
    }

    @DisplayName("부모 카테고리 등록시 오류")
    @Test
    void childSaveException() {
        // given
        CategoryRequest categoryRequest = CategoryRequest.builder()
                .name("test")
                .build();
        Long parentId = categoryService.save(categoryRequest);

        // when
        CategoryRequest categoryRequest2 = CategoryRequest.builder()
                .name("test")
                .depth(2)
                .parentId(parentId + 1L)
                .build();

        assertThatThrownBy(() -> {
            categoryService.save(categoryRequest2);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("부모 카테고리를 찾을 수 없습니다.");
    }

    @DisplayName("부모 카테고리 등록 후 부모 카테고리에서 하위 카테고리 조회")
    @Transactional
    @Test
    void childSaveAndFindChild() {
        // given
        CategoryRequest categoryRequest = CategoryRequest.builder()
                .name("test")
                .build();
        Long parentId = categoryService.save(categoryRequest);

        CategoryRequest categoryRequest2 = CategoryRequest.builder()
                .name("test")
                .depth(2)
                .parentId(parentId)
                .build();

        // when
        Long childId = categoryService.save(categoryRequest2);

        // then
        Category category = categoryRepository.findById(childId)
                .orElseThrow(() -> new IllegalArgumentException("잘못됨"));

        Category parentCategory = categoryRepository.findById(parentId)
                .orElseThrow(() -> new IllegalArgumentException("잘못됨"));

        assertThat(parentCategory.getChild().get(0)).isEqualTo(category);

    }

    @DisplayName("수정 테스트")
    @Transactional
    @Test
    void edit() {
        // given
        CategoryRequest categoryRequest = CategoryRequest.builder()
                .name("test")
                .build();
        Long saveId = categoryService.save(categoryRequest);

        CategoryEdit categoryEdit = CategoryEdit.builder()
                .depth(2)
                .categoryStatus(CategoryStatus.NOUSE)
                .name("test22")
                .build();

        // when
        Long childId = categoryService.edit(categoryEdit, saveId);

        // then
        Category category = categoryRepository.findById(childId)
                .orElseThrow(() -> new IllegalArgumentException("잘못됨"));

        assertThat(category.getName()).isEqualTo(categoryEdit.getName());
        assertThat(category.getCategoryStatus()).isEqualTo(categoryEdit.getCategoryStatus());
        assertThat(category.getDepth()).isEqualTo(categoryEdit.getDepth());
    }
}