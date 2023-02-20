package com.assigment.consumer.service;

import com.assigment.consumer.config.RabbitMQConfig;
import com.assigment.consumer.model.StatisticsInfoMessage;
import com.assigment.consumer.model.UpdateStatisticsMessage;
import com.assigment.consumer.model.Statistics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UpdateStatisticsMessageConsumer {

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "/queue/statistics/update")
    public void consume(@Payload UpdateStatisticsMessage message) {
        try {
            Statistics statistics = statisticsService.getStatisticsByCountryCode(message.getCountryCode());
            statisticsService.updateStatisticsForUser(statistics, message.getUsername());
        } catch (Exception ex) {
            log.error("error occurred during consuming message", ex);
        }
    }

}
