package com.assigment.api.serivce.statistics;

import com.assigment.api.model.statistics.VideoStatisticsMessage;
import com.assigment.core.service.statistics.proto.Stataistics;
import com.assigment.core.service.statistics.proto.StatisticsServiceGrpc;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Controller
public class StatisticsService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GrpcClient("core-service")
    private StatisticsServiceGrpc.StatisticsServiceBlockingStub statisticsServiceGrpc;


    @RabbitListener(queues = "/queue/statistics/info")
    public void sendMessage(VideoStatisticsMessage message) {
        log.info("invoked  sendMessage for user[" + message.getUsername() + "]");
        redisTemplate.opsForValue().set(message.getUsername(), message);
        messagingTemplate.convertAndSendToUser(message.getUsername(),"/topic/statistics-update", message);
    }

    @Cacheable(value = "statisticCache", key = "#userName")
    public VideoStatisticsMessage getStatisticsForUser(String userName) {
        Stataistics.GetStatisticsByUserRequest grpcRequest = Stataistics.GetStatisticsByUserRequest.newBuilder()
                                                                                                    .setUsername(userName)
                                                                                                    .build();
        Stataistics.GetStatisticsByUserResponse grpcResponse = statisticsServiceGrpc.getStatisticsByUser(grpcRequest);
        return VideoStatisticsMessage.builder()
                .commentLink(grpcResponse.getCommentLink())
                .videoLink(grpcResponse.getVideoLink())
                .countryCode(grpcResponse.getCountryCode())
                .countryCode(grpcResponse.getCountryCode())
                .build();
    }
}
