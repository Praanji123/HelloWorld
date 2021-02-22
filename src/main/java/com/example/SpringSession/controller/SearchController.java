package com.example.SpringSession.controller;

import com.example.SpringSession.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class UserController {


    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
        System.out.println("Inside UserController constructor");
    }

    @PostConstruct
    public void init()
    {
        System.out.println("inside UserController Post Constructor");
    }






}
