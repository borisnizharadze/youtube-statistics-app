package com.assigment.api.controller;


import com.assigment.api.model.user.UpdateUserPropsRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Validated
@RequestMapping(value = "/api/user")
public interface UserController {

    @PutMapping
    ResponseEntity<Void> updateUserProperties(@Valid @RequestBody UpdateUserPropsRequest request);
}
