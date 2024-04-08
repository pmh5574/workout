package com.calendar.workout.service.workout;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class WorkoutService {

    private final WorkoutService workoutService;


}
