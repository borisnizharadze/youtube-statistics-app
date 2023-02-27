package com.assigment.api.serivce.auth;

import com.assigment.api.model.auth.LoginRequest;
import com.assigment.api.model.auth.LoginResponse;
import com.assigment.api.model.auth.RegisterRequest;
import com.assigment.api.model.auth.RegisterResponse;
import com.assigment.api.model.exception.ApplicationException;
import com.assigment.api.model.exception.ApplicationExceptionTypes;
import com.assigment.api.model.properties.JksProperties;
import com.assigment.api.model.user.User;
import com.assigment.core.service.auth.proto.Auth;
import com.assigment.core.service.auth.proto.AuthServiceGrpc;
import com.assigment.core.service.user.proto.UserServiceGrpc;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Calendar;
import java.util.Date;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @GrpcClient("core-service")
    private AuthServiceGrpc.AuthServiceBlockingStub grpcAuthService;

    @GrpcClient("core-service")
    private UserServiceGrpc.UserServiceBlockingStub grpcUserService;
    private final RSAPublicKey publicKey;
    private final RSAPrivateKey privateKey;
    private final JksProperties jksProperties;
    private static final int CONSOLE_LIFE_TIME = 120;

    @Autowired
    public AuthServiceImpl(RSAPublicKey publicKey,
                           RSAPrivateKey privateKey,
                           JksProperties jksProperties) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.jksProperties = jksProperties;
    }

    @Override
    public String getCallerUserName(){
        Jwt jwt = getJwt();
        assert jwt != null;
        return  jwt.getSubject();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        Auth.AuthRequest grpcRequest = Auth.AuthRequest.newBuilder()
                .setUserName(request.getUsername())
                .setPassword(request.getPassword())
                .build();

        Auth.AuthResponse grpcResponse;
        try {
            grpcResponse = grpcAuthService.authenticate(grpcRequest);
            if (!grpcResponse.getAuthenticated()) {
                throw new ApplicationException(ApplicationExceptionTypes.INVALID_CREDENTIALS);
            }
        } catch (StatusRuntimeException exception) {
            if (exception.getStatus() == Status.INVALID_ARGUMENT) {
                throw new ApplicationException(ApplicationExceptionTypes.USER_NOT_FOUND);
            }
            throw new ApplicationException(ApplicationExceptionTypes.INTERNAL);
        }

        User user = User.builder()
                .username(grpcResponse.getUsername())
                .countryCode(grpcResponse.getCountryCode())
                .intervalMins(new BigDecimal(grpcResponse.getIntervalMins()))
                .build();

        return LoginResponse.builder()
                .jwt(createAccessToken(user))
                .user(user)
                .build();
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {
        com.assigment.core.service.user.proto.User.CreateUserRequest grpcRequest = com.assigment.core.service.user.proto.User.CreateUserRequest.newBuilder()
                .setUsername(request.getUsername())
                .setPassword(request.getPassword())
                .setCountryCode(request.getCountryCode())
                .setIntervalMins(request.getIntervalMins())
                .build();

        try {
            com.assigment.core.service.user.proto.User.UserResponse grpcResponse = grpcUserService.createUser(grpcRequest);

        } catch (StatusRuntimeException exception) {
            if (exception.getStatus() == Status.ALREADY_EXISTS) {
                throw new ApplicationException(ApplicationExceptionTypes.USER_ALREADY_EXISTS);
            }
        }

        User user = new User(request.getUsername(), new BigDecimal(1), "us");

        return RegisterResponse.builder()
                .jwt(createAccessToken(user))
                .user(user)
                .build();
    }

    private String createAccessToken(User user) {
        return JWT.create()
                .withIssuer(jksProperties.getIssuer())
                .withSubject(user.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(getExpirationTime(Calendar.MINUTE, CONSOLE_LIFE_TIME))
                .sign(Algorithm.RSA256(publicKey, privateKey));
    }

    private Jwt getJwt(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof Jwt) {
                return (Jwt) principal;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private Date getExpirationTime(int unit, int duration){
        Calendar calendar = Calendar.getInstance();
        calendar.add(unit,duration);
        return calendar.getTime();
    }

}
