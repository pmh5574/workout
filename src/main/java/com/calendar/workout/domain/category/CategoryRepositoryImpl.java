package com.calendar.workout.domain.category;

import static com.calendar.workout.domain.category.QCategory.category;

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
}
