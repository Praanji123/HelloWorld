package com.example.SpringSession.services.impl;

import com.example.SpringSession.dto.MyRequestDto;
import com.example.SpringSession.services.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class UserServiceImpl implements UserService {

    public UserServiceImpl()
    {
        System.out.println("Inside UserService Constructor");
    }

    @PostConstruct
    public void init()
    {
        System.out.println("Inside UserService Post Constructor");
    }


    @Override
    public boolean updateEmployee(MyRequestDto request, String id) {
        //process
        System.out.println("inside User Services"+request+id);
        return false;
    }
}
