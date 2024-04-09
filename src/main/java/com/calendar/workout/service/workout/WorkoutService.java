package com.calendar.workout.service.workout;

import com.calendar.workout.domain.exercise.Exercise;
import com.calendar.workout.domain.exercise.ExerciseRepository;
import com.calendar.workout.domain.member.Member;
import com.calendar.workout.domain.member.MemberRepository;
import com.calendar.workout.domain.workout.Workout;
import com.calendar.workout.domain.workout.WorkoutRepository;
import com.calendar.workout.dto.workout.request.WorkoutEdit;
import com.calendar.workout.dto.workout.request.WorkoutRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class WorkoutService {

    private final WorkoutRepository workoutRepository;

    private final MemberRepository memberRepository;

    private final ExerciseRepository exerciseRepository;

    @Transactional
    public Long save(final WorkoutRequest workoutRequest) {

        Workout workout = workoutRequest.toEntity();
        Workout saveWorkout = workoutRepository.save(workout);

        addExercise(workoutRequest.exerciseId(), saveWorkout);
        addMember(workoutRequest.memId(), saveWorkout);

        return saveWorkout.getId();
    }

    @Transactional
    public Long edit(final WorkoutEdit workoutEdit, final Long id) {
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        workout.edit(workoutEdit);

        addExercise(workoutEdit.exerciseId(), workout);
        addMember(workoutEdit.memId(), workout);

        return workout.getId();
    }

    private void addMember(final Long memId, final Workout saveWorkout) {
        if (memId != null) {
            Member member = memberRepository.findById(memId)
                    .orElseThrow(() -> new RuntimeException("없는 회원 정보 입니다."));
            saveWorkout.addMember(member);
        }
    }

    private void addExercise(final Long exerciseId, final Workout saveWorkout) {
        if (exerciseId == null) {
            throw new RuntimeException("운동은 필수 값 입니다.");
        }
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("없는 운동 입니다."));

        saveWorkout.addExercise(exercise);
    }
}
