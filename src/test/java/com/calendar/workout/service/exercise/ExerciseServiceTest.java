package com.calendar.workout.service.exercise;

import static org.assertj.core.api.Assertions.assertThat;

import com.calendar.workout.domain.CategoryStatus;
import com.calendar.workout.domain.category.Category;
import com.calendar.workout.domain.category.CategoryRepository;
import com.calendar.workout.domain.exercise.Exercise;
import com.calendar.workout.domain.exercise.ExerciseRepository;
import com.calendar.workout.domain.exercise.ExerciseType;
import com.calendar.workout.domain.exercise.ToolType;
import com.calendar.workout.dto.exercise.request.ExerciseSaveRequest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class ExerciseServiceTest {

    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    List<Long> categoryIdList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Category category1 = Category.builder()
                .categoryStatus(CategoryStatus.USE)
                .depth(1)
                .name("test1")
                .build();
        Category category2 = Category.builder()
                .categoryStatus(CategoryStatus.USE)
                .depth(2)
                .name("test2")
                .build();

        List<Category> categories = List.of(category1, category2);
        List<Category> categoryList = categoryRepository.saveAll(categories);

        categoryIdList = categoryList.stream()
                .map(Category::getId)
                .toList();
    }

    @AfterEach
    void cleanUp() {
        exerciseRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @DisplayName("등록")
    @Transactional
    @Test
    void save() {
        // given
        ExerciseSaveRequest exerciseSaveRequest = ExerciseSaveRequest.builder()
                .name("벤치 프레스")
                .exerciseType(ExerciseType.WEIGHT_REPETITION)
                .toolType(ToolType.EQUIPMENT)
                .categories(categoryIdList)
                .build();

        // when
        exerciseService.save(exerciseSaveRequest);

        // then
        Exercise exercise = exerciseRepository.findAll().get(0);
        assertThat(exercise.getName()).isEqualTo("벤치 프레스");
        assertThat(exercise.getExerciseCategories().get(0).getCategory().getName()).isEqualTo("test1");
        assertThat(exercise.getExerciseType()).isEqualTo(ExerciseType.WEIGHT_REPETITION);
    }
}