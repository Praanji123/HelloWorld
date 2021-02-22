package com.example.SpringSession.controller;

import com.example.SpringSession.dto.MyRequestDto;
import com.example.SpringSession.dto.MyResponseDto;
import org.springframework.web.bind.annotation.*;

@RestController
public class MyController {



    @GetMapping(path="/hello")
    public String helloWorld()
    {
        return "success!!";
    }

    @PostMapping(path="/hellostart")
    public  String postApi()
    {
        return "Post Success";
    }

    @GetMapping(path="/hello-query")
    public String helloQuery(@RequestParam String query)
    {
        return "query"+query;
    }

    @PostMapping(path="/regist")
    public String registerUser(@RequestBody MyRequestDto myrequestdto )
    {
        return myrequestdto.toString();
    }

    @GetMapping(path="/employee/{employeeId}")
    public MyResponseDto getEmployeeDetails(@PathVariable String employeeId)
    {
        MyResponseDto myResponseDto=new MyResponseDto();
        myResponseDto.setId(employeeId);
        return myResponseDto;
    }






}
