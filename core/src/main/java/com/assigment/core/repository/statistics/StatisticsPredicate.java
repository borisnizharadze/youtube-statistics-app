package com.assigment.core.repository.statistics;

import com.assigment.core.model.entity.QStatisticsEntity;
import com.querydsl.core.types.dsl.BooleanExpression;

import static com.assigment.core.utils.QueryUtils.stringEq;

public class StatisticsPredicate {

    private static final QStatisticsEntity qStatisticsEntity = QStatisticsEntity.statisticsEntity;

    public static BooleanExpression byUserName(String username) {
        return stringEq(qStatisticsEntity.user.username, username);
    }

}
