package com.assigment.consumer.service;


import com.assigment.consumer.model.Statistics;

import java.io.IOException;

public interface StatisticsService {
    void updateStatisticsForUser(Statistics statistics, String username);

    Statistics getStatisticsByCountryCode(String countyCode) throws IOException;
}
