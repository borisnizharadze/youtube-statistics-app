package com.assigment.api.serivce.user;

import com.assigment.api.model.user.UpdateUserPropsRequest;
import com.assigment.api.serivce.auth.AuthService;
import com.assigment.core.service.user.proto.User;
import com.assigment.core.service.user.proto.UserServiceGrpc;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @GrpcClient("core-service")
    private UserServiceGrpc.UserServiceBlockingStub grpcUserService;

    @Autowired
    private AuthService authService;

    @Override
    public void updateUserProperties(UpdateUserPropsRequest request) {
        log.info("invoked updateUserProperties with [" + request + "]");
        User.UpdateUserPropsRequest grpcRequest = User.UpdateUserPropsRequest
                .newBuilder()
                .setCountryCode(request.getCountryCode())
                .setIntervalMins(request.getIntervalMins())
                .setUsername(authService.getCallerUserName())
                .build();

        User.UserResponse grpcResponse = grpcUserService.updateUserProps(grpcRequest);
        log.info("updateUserProperties grpc response [" + grpcResponse + "]");
    }
}
