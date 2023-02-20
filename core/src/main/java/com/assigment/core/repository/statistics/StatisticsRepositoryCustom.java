package com.assigment.core.repository.statistics;


import com.assigment.core.model.entity.StatisticsEntity;

import java.math.BigDecimal;

public interface StatisticsRepositoryCustom {
    void updateStatisticsByUsername(String videoLink, String commentLink, String userName);

    StatisticsEntity getByUsername(String userName);
}
