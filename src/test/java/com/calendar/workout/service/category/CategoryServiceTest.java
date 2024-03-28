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

        assertThatThrownBy(() -> categoryService.save(categoryRequest2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("부모 카테고리를 찾을 수 없습니다.");
    }

    @DisplayName("부모 카테고리 등록시 삭제된 부모라 오류")
    @Transactional
    @Test
    void childSaveDeleteStatusException() {
        // given
        CategoryRequest categoryRequest = CategoryRequest.builder()
                .name("test")
                .build();
        Long parentId = categoryService.save(categoryRequest);

        Category category = categoryRepository.findById(parentId)
                .orElse(null);

        category.changeStatus(CategoryStatus.DELETE);

        // when
        CategoryRequest categoryChildRequest = CategoryRequest.builder()
                .parentId(parentId)
                .name("test")
                .build();

        assertThatThrownBy(() -> categoryService.save(categoryChildRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("부모 카테고리를 찾을 수 없습니다.");
    }

    @DisplayName("부모 카테고리 등록시 부모보다 뎁스가 낮으면 오류")
    @Transactional
    @Test
    void childSaveNotLowDepthException() {
        // given
        CategoryRequest categoryRequest = CategoryRequest.builder()
                .depth(1)
                .name("test")
                .build();
        Long parentId = categoryService.save(categoryRequest);

        // when
        CategoryRequest categoryChildRequest = CategoryRequest.builder()
                .parentId(parentId)
                .depth(1)
                .name("test")
                .build();

        assertThatThrownBy(() -> categoryService.save(categoryChildRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("부모 카테고리 뎁스보다 커야 합니다.");
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
        Category category = Category.builder()
                .name("test")
                .categoryStatus(CategoryStatus.USE)
                .depth(1)
                .build();
        Category saveCategory = categoryRepository.save(category);

        CategoryEdit categoryEdit = CategoryEdit.builder()
                .categoryStatus(CategoryStatus.NOUSE)
                .name("test22")
                .build();

        // when
        Long childId = categoryService.edit(categoryEdit, saveCategory.getId());

        // then
        Category changeCategory = categoryRepository.findById(childId)
                .orElseThrow(() -> new IllegalArgumentException("잘못됨"));

        assertThat(changeCategory.getName()).isEqualTo(categoryEdit.getName());
        assertThat(changeCategory.getCategoryStatus()).isEqualTo(categoryEdit.getCategoryStatus());
        assertThat(changeCategory.getDepth()).isEqualTo(categoryEdit.getDepth());
    }

    @DisplayName("부모 카테고리 수정시 오류")
    @Transactional
    @Test
    void childEditException() {
        // given
        Category category = Category.builder()
                .name("test")
                .categoryStatus(CategoryStatus.USE)
                .depth(1)
                .build();
        Category saveCategory = categoryRepository.save(category);

        Category childCategoryData = Category.builder()
                .parent(saveCategory)
                .categoryStatus(CategoryStatus.USE)
                .name("test")
                .depth(1)
                .build();
        Category childCategory = categoryRepository.save(childCategoryData);

        CategoryEdit categoryEdit = CategoryEdit.builder()
                .depth(2)
                .parentId(childCategory.getId() + 1)
                .categoryStatus(CategoryStatus.NOUSE)
                .name("test22")
                .build();

        // when

        assertThatThrownBy(() -> categoryService.edit(categoryEdit, childCategory.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("부모 카테고리를 찾을 수 없습니다.");
    }

    @DisplayName("부모 카테고리 수정시 부모값이 삭제된 값이라 오류")
    @Transactional
    @Test
    void childEditDeleteStatusException() {
        // given
        Category category = Category.builder()
                .name("test")
                .categoryStatus(CategoryStatus.USE)
                .depth(1)
                .build();
        Category saveCategory = categoryRepository.save(category);

        Category childCategoryData = Category.builder()
                .parent(saveCategory)
                .categoryStatus(CategoryStatus.USE)
                .name("test")
                .depth(1)
                .build();
        Category childCategory = categoryRepository.save(childCategoryData);

        CategoryEdit categoryEdit = CategoryEdit.builder()
                .depth(2)
                .parentId(saveCategory.getId())
                .categoryStatus(CategoryStatus.NOUSE)
                .name("test22")
                .build();

        // when
        saveCategory.changeStatus(CategoryStatus.DELETE);

        assertThatThrownBy(() -> categoryService.edit(categoryEdit, childCategory.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("부모 카테고리를 찾을 수 없습니다.");
    }

    @DisplayName("부모 카테고리 수정시 부모값보가 뎁스가 작으면 오류")
    @Transactional
    @Test
    void childEditNotLowDepthException() {
        // given
        Category category = Category.builder()
                .name("test")
                .categoryStatus(CategoryStatus.USE)
                .depth(2)
                .build();
        Category saveCategory = categoryRepository.save(category);

        Category childCategoryData = Category.builder()
                .parent(saveCategory)
                .categoryStatus(CategoryStatus.USE)
                .name("test")
                .depth(3)
                .build();
        Category childCategory = categoryRepository.save(childCategoryData);

        CategoryEdit categoryEdit = CategoryEdit.builder()
                .depth(2)
                .parentId(saveCategory.getId())
                .categoryStatus(CategoryStatus.NOUSE)
                .name("test22")
                .build();

        // when
        assertThatThrownBy(() -> categoryService.edit(categoryEdit, childCategory.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("부모 카테고리 뎁스보다 커야 합니다.");
    }

    @DisplayName("값 등록시 부모 값 없이 2뎁스 이상이면 오류")
    @Transactional
    @Test
    void saveCheckParentException() {
        // given
        CategoryRequest categoryRequest = CategoryRequest.builder()
                .name("test")
                .categoryStatus(CategoryStatus.USE)
                .depth(2)
                .build();

        // when
        assertThatThrownBy(() -> categoryService.save(categoryRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("부모 값 없이 2뎁스를 설정할 수 없습니다.");
    }

    @DisplayName("값 수정시 부모 값 없이 2뎁스 이상이면 오류")
    @Transactional
    @Test
    void editCheckParentException() {
        // given
        Category category = Category.builder()
                .name("test")
                .categoryStatus(CategoryStatus.USE)
                .depth(1)
                .build();
        Category saveCategory = categoryRepository.save(category);

        CategoryEdit categoryEdit = CategoryEdit.builder()
                .depth(2)
                .build();

        // when
        assertThatThrownBy(() -> categoryService.edit(categoryEdit, saveCategory.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("부모 값 없이 2뎁스를 설정할 수 없습니다.");
    }
}