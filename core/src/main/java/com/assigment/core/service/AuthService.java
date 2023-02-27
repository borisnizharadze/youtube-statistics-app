package com.assigment.core.service;

import com.assigment.core.model.entity.UserEntity;
import com.assigment.core.repository.user.UserRepository;
import com.assigment.core.service.auth.proto.Auth;
import com.assigment.core.service.auth.proto.AuthServiceGrpc;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Slf4j
@GrpcService
public class AuthService extends AuthServiceGrpc.AuthServiceImplBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void authenticate(Auth.AuthRequest request, StreamObserver<Auth.AuthResponse> responseObserver) {
        try {
            Auth.AuthResponse reply;
            UserEntity user = Optional.ofNullable(userRepository.findByUsername(request.getUserName()))
                    .orElseThrow(IllegalArgumentException::new);

            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                reply = Auth.AuthResponse.newBuilder()
                        .setAuthenticated(true)
                        .setCountryCode(user.getCountryCode())
                        .setIntervalMins(user.getIntervalMins())
                        .setUsername(user.getUsername())
                        .build();
            } else {
                reply = Auth.AuthResponse.newBuilder()
                        .setAuthenticated(false)
                        .build();
            }

            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        } catch (IllegalArgumentException illegalArgumentException) {
            Status status = Status.UNAUTHENTICATED;
            responseObserver.onError(status.asRuntimeException());
        }
    }
}
