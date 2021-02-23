package com.example.SpringSession.service.impl;

import com.example.SpringSession.client.SearchClient;
import com.example.SpringSession.dto.ProductDTO;
import com.example.SpringSession.dto.SearchRequestdto;
import com.example.SpringSession.dto.SearchResponseDto;
import com.example.SpringSession.service.SearchService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchClient searchClient;

    @Override
    public SearchResponseDto getProducts(SearchRequestdto requestdto) {

        Map<String,Object> locations=searchClient.getProducts("stockLocation:"+requestdto.getLocation());


        List<Map<String,Object>> productLocationList=(List<Map<String, Object>>)((Map)locations.get("response")).get("docs");

        SearchResponseDto responseDto=new SearchResponseDto();


        ExecutorService executor = Executors.newFixedThreadPool(2);
        Runnable task = () -> {
            List<ProductDTO> list =  getStringObjectMap(requestdto.getSearchTerm());
            responseDto.setProducts(list);
        };
        task.run();

        List<ProductDTO> list2=new ArrayList<>();
        Runnable task2 = () -> {
            for(int i=0;i<productLocationList.size();i++)
            {
                ProductDTO productDTO=new ProductDTO();
                String brandName=productLocationList.get(i).get("name").toString();
                String  description=productLocationList.get(i).get("description").toString();
                //salePrice
                int  salePrice= ((Double) productLocationList.get(i).get("salePrice")).intValue();
                boolean inStock=(int)productLocationList.get(i).get("isInStock")==1?true:false;
                productDTO.setDescription(description);
                productDTO.setTitle(brandName);
                productDTO.setSalesPrice(salePrice);
                productDTO.setInStock(inStock);
                list2.add(productDTO);
            }

            responseDto.setLocationBasedProd(list2);
        };

        executor.execute(task);
        executor.execute(task2);
        executor.shutdown();
        try {
            executor.awaitTermination(60, TimeUnit.SECONDS);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        return responseDto;
    }

    private List<ProductDTO> getStringObjectMap(String query) {

        List<ProductDTO> list=new ArrayList<>();
        Map<String, Object> products= searchClient.getProducts(query);
        List<Map<String,Object>> productObjectList=(List<Map<String, Object>>)((Map)products.get("response")).get("docs");
        for(int i=0;i<productObjectList.size();i++)
        {
            ProductDTO productDTO=new ProductDTO();
            String brandName=productObjectList.get(i).get("name").toString();
            String  description=productObjectList.get(i).get("description").toString();
            //salePrice
            int  salePrice= ((Double) productObjectList.get(i).get("salePrice")).intValue();
            boolean inStock=(int)productObjectList.get(i).get("isInStock")==1?true:false;
            productDTO.setDescription(description);
            productDTO.setTitle(brandName);
            productDTO.setSalesPrice(salePrice);
            productDTO.setInStock(inStock);
            list.add(productDTO);
        }
        return list;
    }
}
