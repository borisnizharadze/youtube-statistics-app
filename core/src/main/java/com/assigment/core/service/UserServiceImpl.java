package com.assigment.core.service;


import com.assigment.core.config.RabbitMQConfig;
import com.assigment.core.model.entity.StatisticsEntity;
import com.assigment.core.model.entity.UserEntity;
import com.assigment.core.model.message.UpdateStatisticsMessage;
import com.assigment.core.repository.statistics.StatisticsRepository;
import com.assigment.core.repository.user.UserRepository;
import com.assigment.core.service.user.proto.User;
import com.assigment.core.service.user.proto.UserServiceGrpc;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Slf4j
@GrpcService
public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void createUser(User.CreateUserRequest request, StreamObserver<User.UserResponse> responseObserver) {
        try {
            Optional.ofNullable(userRepository.findByUsername(request.getUsername()))
                    .ifPresent((s) -> {throw new IllegalArgumentException();});

            User.UserResponse reply = User.UserResponse
                                            .newBuilder()
                                            .setUsername(request.getUsername())
                                            .setCountryCode(request.getCountryCode())
                                            .setIntervalMins(request.getIntervalMins())
                                            .build();

            UserEntity user = UserEntity.builder()
                                        .intervalMins(request.getIntervalMins())
                                        .countryCode(request.getCountryCode())
                                        .username(request.getUsername())
                                        .password(passwordEncoder.encode(request.getPassword()))
                                        .lastStatisticsUpdate(new Date())
                                        .build();

            user = userRepository.save(user);

            StatisticsEntity statistics = StatisticsEntity.builder()
                                                        .commentLink("")
                                                        .videoLink("")
                                                        .user(user)
                                                        .build();
            statisticsRepository.save(statistics);

            UpdateStatisticsMessage message = new UpdateStatisticsMessage(user.getUsername(), user.getCountryCode());
            rabbitTemplate.convertAndSend(RabbitMQConfig.STATISTICS_UPDATE_EXCHANGE, RabbitMQConfig.STATISTICS_UPDATE_ROUTING_KEY, message);
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        } catch (IllegalArgumentException illegalArgumentException) {
            Status status = Status.ALREADY_EXISTS;
            responseObserver.onError(status.asRuntimeException());
        }
    }

    @Override
    public void updateUserProps(User.UpdateUserPropsRequest request, StreamObserver<User.UserResponse> responseObserver) {
        try {
            UserEntity user = Optional.ofNullable(userRepository.findByUsername(request.getUsername()))
                    .orElseThrow(IllegalArgumentException::new);

            User.UserResponse reply = User.UserResponse
                    .newBuilder()
                    .setUsername(request.getUsername())
                    .setCountryCode(request.getCountryCode())
                    .setIntervalMins(request.getIntervalMins())
                    .build();

            user.setCountryCode(request.getCountryCode());
            user.setIntervalMins(request.getIntervalMins());

            userRepository.save(user);

            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        } catch (IllegalArgumentException illegalArgumentException) {
            Status status = Status.NOT_FOUND;
            responseObserver.onError(status.asRuntimeException());
        }
    }

    @Override
    public void getUsersForStatisticsUpdate(User.Empty request, StreamObserver<User.Users> responseObserver) {
        List<User.UserResponse> userResponseList = userRepository.getUsersForStatisticsUpdate()
                .stream()
                .map(userEntity -> User.UserResponse
                        .newBuilder()
                        .setCountryCode(userEntity.getCountryCode())
                        .setIntervalMins(userEntity.getIntervalMins())
                        .setUsername(userEntity.getUsername())
                        .build())

                .collect(Collectors.toList());

        User.Users reply = User.Users
                                .newBuilder()
                                .addAllUsers(userResponseList)
                                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
