package com.assigment.api.controller;


import com.assigment.api.model.user.UpdateUserPropsRequest;
import com.assigment.api.serivce.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserControllerImpl implements UserController {

    @Autowired
    private UserService userService;


    @Override
    public ResponseEntity<Void> updateUserProperties(UpdateUserPropsRequest request) {
        userService.updateUserProperties(request);
        return ResponseEntity.ok().build();
    }
}
