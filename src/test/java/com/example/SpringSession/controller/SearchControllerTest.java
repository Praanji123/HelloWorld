package com.example.SpringSession.controller;

import com.example.SpringSession.client.SearchClient;
import com.example.SpringSession.dto.SearchRequestdto;
import com.example.SpringSession.dto.SearchResponseDto;
import com.example.SpringSession.service.SearchService;
import com.example.SpringSession.service.impl.SearchServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
class SearchControllerTest {



    @InjectMocks
    private SearchServiceImpl searchService;

    @Mock
    private SearchClient searchClient;

    @BeforeEach
    public void init()
    {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void getProducts() throws IOException {
        ObjectMapper objectMapper=new ObjectMapper();
        Map<String,Object> searchTermMockObject=objectMapper.readValue(new URL("file:src/main/resources/search.mock"),Map.class);

        Map<String,Object> locationMockObject=objectMapper.readValue(new URL("file:src/main/resources/location.mock"),Map.class);


        Mockito.when(searchClient.getProducts("samsung")).thenReturn(searchTermMockObject);
        Mockito.when(searchClient.getProducts("stockLocation:\"jakarta\"")).thenReturn(locationMockObject);
        SearchRequestdto requestdto=new SearchRequestdto();
        requestdto.setSearchTerm("samsung");
        requestdto.setLocation("jakarta");
        SearchResponseDto response=searchService.getProducts(requestdto);

        assertEquals(response.getProducts().size(),10);
        assertEquals(response.getLocationBasedProd().size(),10);

        //Mockito.verify(searchClient.getProducts("samsung"));
        //Mockito.verify(searchClient.getProducts("stockLocation:\"jakarta\""));





    }

    @AfterEach
    public void teardown()
    {





    }
    @Test
    void testGetLocation()
    {

    }

    @Test
    public void testGetProductsExceptionTest() throws IOException {
        ObjectMapper objectMapper=new ObjectMapper();
        Map<String,Object> searchTermMockObject=objectMapper.readValue(new URL("file:src/main/resources/search.mock"),Map.class);

        Map<String,Object> locationMockObject=objectMapper.readValue(new URL("file:src/main/resources/location.mock"),Map.class);


        Mockito.when(searchClient.getProducts("samsung")).thenReturn(searchTermMockObject);
        Mockito.when(searchClient.getProducts("stockLocation:\"jakarta\"")).thenThrow(NullPointerException.class);
        SearchRequestdto requestdto=new SearchRequestdto();
        requestdto.setSearchTerm("samsung");
        requestdto.setLocation("jakarta");
        SearchResponseDto response=searchService.getProducts(requestdto);

        assertEquals(response.getProducts().size(),10);
        assertEquals(response.getLocationBasedProd(),null);



    }





}