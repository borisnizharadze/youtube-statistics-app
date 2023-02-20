package com.assigment.api.controller;

import com.assigment.api.model.statistics.VideoStatisticsMessage;
import com.assigment.api.serivce.statistics.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class StatisticController {

    @Autowired
    private StatisticsService statisticsService;

    @MessageMapping("/connect")
    @SendToUser("/topic/statistics-update")
    public VideoStatisticsMessage connect(Principal principal) {
        return statisticsService.getStatisticsForUser(principal.getName());
    }
}
