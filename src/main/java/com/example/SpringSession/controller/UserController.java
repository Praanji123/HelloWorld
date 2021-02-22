package com.example.SpringSession.controller;


import com.example.SpringSession.dto.MyRequestDto;
import com.example.SpringSession.dto.MyResponseDto;
import com.example.SpringSession.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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




    @PostMapping(path="/update/employee/{id}")
    public boolean updateEmployee(@PathVariable String id, @RequestBody MyRequestDto request)
    {
        return  userService.updateEmployee(request,id);
    }

}
