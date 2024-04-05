package com.calendar.workout.domain.category;

import com.calendar.workout.dto.category.request.CategorySearch;
import java.util.List;

public interface CategoryRepositoryCustom {

    List<Category> findByCategories(List<Long> categories);

    List<Category> getList(CategorySearch categorySearch);
}
