package com.calendar.workout.domain.category;

import java.util.List;

public interface CategoryRepositoryCustom {

    List<Category> findByCategories(List<Long> categories);
}
