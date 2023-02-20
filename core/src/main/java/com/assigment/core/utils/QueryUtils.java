package com.assigment.core.utils;

import com.querydsl.core.types.Ops;
import com.querydsl.core.types.dsl.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
public class QueryUtils {

    public static BooleanExpression where(BooleanExpression... predicates) {
        return Optional.ofNullable(predicates)
                .map(Arrays::asList)
                .orElseGet(List::of)
                .stream()
                .reduce(True(), BooleanExpression::and);
    }

    public static BooleanExpression stringEq(StringPath stringField, String s) {
        return StringUtils.isBlank(s) ? True() : stringField.eq(s.trim());
    }

    public static BooleanExpression datePlusIntervalLess(DateTimePath<Date> timeField, NumberPath<Integer> intervalField, Date time) {
        return time == null ? True() : Expressions.dateOperation(Date.class, Ops.DateTimeOps.ADD_MINUTES, intervalField, timeField).after(time);
    }

    public static BooleanExpression True() {
        return Expressions.asBoolean(true).isTrue();
    }


}
