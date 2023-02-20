package com.assigment.api.controller;

import com.assigment.api.model.auth.LoginRequest;
import com.assigment.api.model.auth.LoginResponse;
import com.assigment.api.model.auth.RegisterRequest;
import com.assigment.api.model.auth.RegisterResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Validated
@RequestMapping(value = "/auth")
public interface AuthController {

    @PostMapping("login")
    ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request);

    @PostMapping("register")
    ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request);
}
