package com.assigment.api.controller;

import com.assigment.api.model.auth.LoginRequest;
import com.assigment.api.model.auth.LoginResponse;
import com.assigment.api.model.auth.RegisterRequest;
import com.assigment.api.model.auth.RegisterResponse;
import com.assigment.api.serivce.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthControllerImpl implements AuthController {

    @Autowired
    private AuthService authService;

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Override
    public ResponseEntity<RegisterResponse> register(RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
}
