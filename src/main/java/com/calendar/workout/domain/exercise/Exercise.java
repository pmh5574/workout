package com.calendar.workout.domain.exercise;

import com.calendar.workout.domain.category.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Set<Category> categories;

}
