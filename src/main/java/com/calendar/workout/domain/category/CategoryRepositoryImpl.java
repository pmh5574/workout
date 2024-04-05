package com.calendar.workout.domain.category;

import static com.calendar.workout.domain.category.QCategory.category;

import com.calendar.workout.dto.category.request.CategorySearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Category> findByCategories(final List<Long> categories) {
        return jpaQueryFactory.selectFrom(category)
                .where(category.id.in(categories))
                .fetch();
    }

    @Override
    public List<Category> getList(final CategorySearch categorySearch) {
        return jpaQueryFactory.selectFrom(category)
                .limit(categorySearch.getSize())
                .offset(categorySearch.getOffset())
                .orderBy(category.id.desc())
                .fetch();
    }


}
