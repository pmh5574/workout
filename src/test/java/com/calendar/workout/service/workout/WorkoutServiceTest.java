package com.calendar.workout.service.workout;

import static org.assertj.core.api.Assertions.assertThat;

import com.calendar.workout.domain.exercise.Exercise;
import com.calendar.workout.domain.exercise.ExerciseRepository;
import com.calendar.workout.domain.exercise.ExerciseType;
import com.calendar.workout.domain.exercise.ToolType;
import com.calendar.workout.domain.workout.Workout;
import com.calendar.workout.domain.workout.WorkoutRepository;
import com.calendar.workout.dto.workout.request.WorkoutEdit;
import com.calendar.workout.dto.workout.request.WorkoutRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class WorkoutServiceTest {

    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    private Long exerciseId;
    private Exercise exercise;

    @BeforeEach
    void setUp() {
        Exercise exercise = Exercise.builder()
                .name("스쿼트")
                .exerciseType(ExerciseType.WEIGHT_REPETITION)
                .toolType(ToolType.BODY)
                .build();
        this.exercise = exerciseRepository.save(exercise);
        this.exerciseId = exercise.getId();
    }

    @Transactional
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
                .exerciseId(exerciseId)
                .build();

        // when
        Long saveId = workoutService.save(workoutRequest);

        // then
        Workout workout = workoutRepository.findById(saveId)
                .orElseThrow(RuntimeException::new);

        assertThat(workout.getId()).isEqualTo(saveId);
        assertThat(workout.getReps()).isEqualTo(5);
        assertThat(workout.getRestTime()).isEqualTo(LocalTime.of(0, 0, 30));
    }

    @Transactional
    @DisplayName("운동 수정 테스트")
    @Test
    void edit() {
        // given
        Workout saveWorkout = Workout.builder()
                .reps(5)
                .weights(80)
                .restTime(LocalTime.of(0, 0, 30))
                .workoutDate(LocalDate.now())
                .memo("test중")
                .build();
        Workout save = workoutRepository.save(saveWorkout);
        save.addExercise(exercise);

        WorkoutEdit workoutEdit = WorkoutEdit.builder()
                .reps(10)
                .weights(50)
                .restTime(LocalTime.of(0, 0, 30))
                .workoutDate(LocalDate.now())
                .memo("test중")
                .exerciseId(exerciseId)
                .build();

        // when
        Long editId = workoutService.edit(workoutEdit, save.getId());

        // then
        Workout workout = workoutRepository.findById(editId)
                .orElseThrow(RuntimeException::new);

        assertThat(workout.getId()).isEqualTo(editId);
    }

}