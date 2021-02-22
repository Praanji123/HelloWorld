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


@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchClient searchClient;

    @Override
    public SearchResponseDto getProducts(SearchRequestdto requestdto) {

        Map<String, Object> products= searchClient.getProducts(requestdto.getSearchTerm());

        List<Map<String,Object>> productObjectList=(List<Map<String, Object>>)((Map)products.get("response")).get("docs");
        SearchResponseDto responseDto=new SearchResponseDto();
        List<ProductDTO> list=new ArrayList<>();
        for(int i=0;i<productObjectList.size();i++)
        {
            ProductDTO productDTO=new ProductDTO();
            String brandName=productObjectList.get(i).get("name").toString();
            String  description=productObjectList.get(i).get("description").toString();
            //salePrice
            int  salePrice= ((Double) productObjectList.get(i).get("salePrice")).intValue();
            productDTO.setDescription(description);
            productDTO.setTitle(brandName);
            productDTO.setSalesPrice(salePrice);
            productDTO.setInStock(true);
            list.add(productDTO);
        }
        responseDto.setProducts(list);

        System.out.println(products);
        return responseDto;
    }
}
