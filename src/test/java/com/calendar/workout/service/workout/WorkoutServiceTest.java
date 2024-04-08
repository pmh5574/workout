package com.calendar.workout.service.workout;

import com.calendar.workout.domain.workout.WorkoutRepository;
import com.calendar.workout.dto.workout.request.WorkoutRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WorkoutServiceTest {

    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private WorkoutRepository workoutRepository;

    @DisplayName("운동 등록 테스트")
    @Test
    void save() {
        // given
        WorkoutRequest workoutRequest = WorkoutRequest.builder()
                .reps(5)
                .weights(80)
                .restTime(LocalTime.of(0, 0, 30))
                .workoutDate(LocalDate.now())
                .memo("test중")
                .build();

        // when

        // then
    }

}