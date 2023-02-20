package com.assigment.producer.service;

import com.assigment.core.service.user.proto.User;
import com.assigment.producer.config.RabbitMQConfig;
import com.assigment.producer.model.UpdateStatisticsMessage;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.assigment.core.service.user.proto.UserServiceGrpc;

@Slf4j
@Service
public class UpdateStatisticsMessageProducer {

    private final RabbitTemplate rabbitTemplate;

    @GrpcClient("core-service")
    private UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    @Autowired
    public UpdateStatisticsMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedDelay = 30000L)
    public void produceMessage() {
        User.Users users = userServiceBlockingStub.getUsersForStatisticsUpdate(User.Empty.newBuilder().build());
        users.getUsersList().parallelStream().forEach(userResponse -> {
            UpdateStatisticsMessage message = new UpdateStatisticsMessage(userResponse.getUsername(), userResponse.getCountryCode());
            rabbitTemplate.convertAndSend(RabbitMQConfig.STATISTICS_UPDATE_EXCHANGE, RabbitMQConfig.STATISTICS_UPDATE_ROUTING_KEY, message);
        });
    }



}
