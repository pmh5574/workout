package com.calendar.workout.service.exercise;

import com.calendar.workout.domain.exercise.ExerciseRepository;
import com.calendar.workout.dto.exercise.request.ExerciseSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Transactional
    public void save(ExerciseSaveRequest exerciseSaveRequest) {

    }
}
