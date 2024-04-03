package com.calendar.workout.service.exercise;

import com.calendar.workout.domain.category.Category;
import com.calendar.workout.domain.category.CategoryRepository;
import com.calendar.workout.domain.exercise.CategoryExercise;
import com.calendar.workout.domain.exercise.CategoryExerciseRepository;
import com.calendar.workout.domain.exercise.Exercise;
import com.calendar.workout.domain.exercise.ExerciseRepository;
import com.calendar.workout.dto.exercise.request.ExerciseEditRequest;
import com.calendar.workout.dto.exercise.request.ExerciseSaveRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    private final CategoryExerciseRepository categoryExerciseRepository;

    private final CategoryRepository categoryRepository;

    @Transactional
    public Long save(final ExerciseSaveRequest exerciseSaveRequest) {
        Exercise exercise = exerciseSaveRequest.toEntity();

        Exercise saveExercise = exerciseRepository.save(exercise);

        if (!exerciseSaveRequest.getCategories().isEmpty()) {
            saveExercise.addCategoryExerciseList(
                    saveCategoryExercise(exerciseSaveRequest.getCategories(), saveExercise));
        }

        return saveExercise.getId();
    }

    @Transactional
    public Long edit(final ExerciseEditRequest exerciseEditRequest, Long exerciseId) {
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("잘못된 요청 입니다."));

        exercise.edit(exerciseEditRequest);

        if (!exerciseEditRequest.getCategories().isEmpty()) {
            exercise.removeCategoryExercise();
            exercise.addCategoryExerciseList(
                    saveCategoryExercise(exerciseEditRequest.getCategories(), exercise));
        }

        return exercise.getId();
    }

    private List<CategoryExercise> saveCategoryExercise(final List<Long> categoryIdLit, final Exercise saveExercise) {

        List<Category> categories = categoryRepository.findByCategories(categoryIdLit);

        List<CategoryExercise> categoryExercises = categories.stream()
                .map(category -> CategoryExercise.builder()
                        .exercise(saveExercise)
                        .category(category)
                        .build())
                .toList();

        return categoryExerciseRepository.saveAll(categoryExercises);
    }
}
