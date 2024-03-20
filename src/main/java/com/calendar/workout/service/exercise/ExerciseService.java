package com.calendar.workout.service.exercise;

import com.calendar.workout.domain.exercise.ExerciseRepository;
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

    @Transactional
    public void save(final ExerciseSaveRequest exerciseSaveRequest, final List<Long> categoryIds) {
//        exerciseSaveRequest.
    }
}
