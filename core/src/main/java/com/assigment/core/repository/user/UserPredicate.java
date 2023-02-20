package com.assigment.core.repository.user;

import com.assigment.core.model.entity.QUserEntity;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.Date;

import static com.assigment.core.utils.QueryUtils.*;

public class UserPredicate {
    private static final QUserEntity qUserEntity = QUserEntity.userEntity;

    public static BooleanExpression byUserName(String userName) {
        return stringEq(qUserEntity.username, userName);
    }

    public static BooleanExpression byOverdueUpdate(Date date) {
        return datePlusIntervalLess(qUserEntity.lastStatisticsUpdate, qUserEntity.intervalMins, date);
    }

}
