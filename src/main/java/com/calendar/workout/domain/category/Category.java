package com.calendar.workout.domain.category;

import static lombok.AccessLevel.PROTECTED;

import com.calendar.workout.domain.BaseTimeEntity;
import com.calendar.workout.domain.exercise.CategoryExercise;
import com.calendar.workout.dto.category.request.CategoryEdit;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false)
    private Integer depth;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryStatus categoryStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent = null;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Category> child = new ArrayList<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<CategoryExercise> categoryExercises = new ArrayList<>();

    @Builder
    public Category(Category parent, Integer depth, String name, CategoryStatus categoryStatus) {
        this.parent = parent;
        this.depth = depth;
        this.name = name;
        this.categoryStatus = categoryStatus;
    }

    public void addParentCategory(Category parentCategory) {
        checkDeleteStatus(parentCategory.getCategoryStatus());

        checkDepth(parentCategory.getDepth());

        this.parent = parentCategory;

        parentCategory.addChild(this);
    }

    public void edit(CategoryEdit categoryRequest) {
        if (categoryRequest.getCategoryStatus() != null) {
            this.categoryStatus = categoryRequest.getCategoryStatus();
        }

        if (categoryRequest.getDepth() != null) {
            this.depth = categoryRequest.getDepth();
            checkParentDepth();
        }

        this.name = categoryRequest.getName();

    }

    public void changeStatus(CategoryStatus categoryStatus) {
        this.categoryStatus = categoryStatus;
    }

    private void checkParentDepth() {
        if (this.parent != null) {
            checkDepth(this.parent.getDepth());
        }
    }

    private void addChild(Category... children) {
        this.child.addAll(Arrays.asList(children));
    }

    private void checkDeleteStatus(CategoryStatus parentCategoryStatus) {
        if (parentCategoryStatus == CategoryStatus.DELETE) {
            throw new IllegalArgumentException("부모 카테고리를 찾을 수 없습니다.");
        }
    }

    private void checkDepth(Integer parentDepth) {
        if (parentDepth >= this.depth) {
            throw new IllegalArgumentException("부모 카테고리 뎁스보다 커야 합니다.");
        }
    }

}
