package com.assigment.core.repository.statistics;

import com.assigment.core.model.entity.StatisticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.stereotype.Repository;

public interface StatisticsRepository extends JpaRepository<StatisticsEntity, Long>,
                                            QuerydslPredicateExecutor<StatisticsEntity>,
                                            StatisticsRepositoryCustom {
}
