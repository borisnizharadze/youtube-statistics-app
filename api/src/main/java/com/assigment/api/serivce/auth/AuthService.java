package com.assigment.api.serivce.auth;

import com.assigment.api.model.auth.LoginRequest;
import com.assigment.api.model.auth.LoginResponse;
import com.assigment.api.model.auth.RegisterRequest;
import com.assigment.api.model.auth.RegisterResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);

    RegisterResponse register(RegisterRequest request);

    String getCallerUserName();
}
