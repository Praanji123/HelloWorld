package com.example.SpringSession.controller;

import com.example.SpringSession.dto.SearchRequestdto;
import com.example.SpringSession.dto.SearchResponseDto;
import com.example.SpringSession.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SearchController {


    @Autowired
    private SearchService searchService;

    @PostMapping(path="/search")
    public SearchResponseDto getProductDetails(@RequestBody SearchRequestdto request)
    {
        String location=request.getLocation();
        String search=request.getSearchTerm();
        return  searchService.getProducts(request);
    }














}
