package com.assigment.core.service;

import com.assigment.core.config.RabbitMQConfig;
import com.assigment.core.model.entity.StatisticsEntity;
import com.assigment.core.model.message.VideoStatisticsMessage;
import com.assigment.core.repository.statistics.StatisticsRepository;
import com.assigment.core.service.statistics.proto.Stataistics;
import com.assigment.core.service.statistics.proto.StatisticsServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@GrpcService
public class StatisticService extends StatisticsServiceGrpc.StatisticsServiceImplBase {

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void getStatisticsByUser(Stataistics.GetStatisticsByUserRequest request, StreamObserver<Stataistics.GetStatisticsByUserResponse> responseObserver) {
        StatisticsEntity statistics = statisticsRepository.getByUsername(request.getUsername());
        Stataistics.GetStatisticsByUserResponse reply = Stataistics.GetStatisticsByUserResponse.newBuilder()
                .setVideoLink(statistics.getVideoLink())
                .setCommentLink(statistics.getCommentLink())
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    @Override
    public void updateStatisticsForUser(Stataistics.UpdateStatisticsForUserRequest request, StreamObserver<Stataistics.UpdateStatisticsForUserResponse> responseObserver) {
        Stataistics.UpdateStatisticsForUserResponse reply = Stataistics.UpdateStatisticsForUserResponse.newBuilder()
                .setUpdated(true)
                .build();

        statisticsRepository.updateStatisticsByUsername(request.getVideoLink(),
                                                        request.getCommentLink(),
                                                        request.getUsername());

        rabbitTemplate.convertAndSend(RabbitMQConfig.STATISTICS_INFO_EXCHANGE,
                                      RabbitMQConfig.STATISTICS_INFO_BINDING_KEY,
                                      VideoStatisticsMessage.builder()
                                                            .username(request.getUsername())
                                                            .commentLink(request.getCommentLink())
                                                            .videoLink(request.getVideoLink())
                                                            .countryCode(request.getCountryCode())
                                                            .build());

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
