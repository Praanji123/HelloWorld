package com.example.SpringSession.service.impl;

import com.example.SpringSession.client.SearchClient;
import com.example.SpringSession.constants.SolrFieldNames;
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
    private  static final int POOL_SIZE = 2;

    @Autowired
    private SearchClient searchClient;

    @Override
    public SearchResponseDto getProducts(SearchRequestdto request) {

        SearchResponseDto responseDTO = new SearchResponseDto();
        ExecutorService threadPool = Executors.newFixedThreadPool(POOL_SIZE);

        threadPool.execute(() -> {
            String searchTermQuery = request.getSearchTerm();
            List<ProductDTO> productDTOS = getSearchTermBaseProducts(searchTermQuery);
            responseDTO.setProducts(productDTOS);
        });

        threadPool.execute(() -> {
            String locationQuery = SolrFieldNames.STOCK_LOCATION + ":\"" + request.getLocation() + "\"";
            List<ProductDTO> locationProductDTOs = getSearchTermBaseProducts(locationQuery);
            responseDTO.setLocationBasedProd(locationProductDTOs);
        });

        awaitTerminationAfterShutdown(threadPool);
        return responseDTO;
    }

    private void awaitTerminationAfterShutdown(ExecutorService threadPool) {
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException ex) {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private List<ProductDTO> getSearchTermBaseProducts(String query) {
        Map<String, Object> productResponse = searchClient.getProducts(query);
        Map<String, Object> responseObject = (Map<String, Object>) (productResponse.get("response"));
        List<Map<String, Object>> products = (List<Map<String, Object>>) responseObject.get("docs");
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Map<String, Object> productClientResponse :products) {
            String title = (String) productClientResponse.get(SolrFieldNames.NAME);
            boolean inStock = (int) productClientResponse.get(SolrFieldNames.IN_STOCK) == 1? true: false;
            String description = (String) productClientResponse.get(SolrFieldNames.DESCRIPTION);

            ProductDTO productDTO = new ProductDTO();
            productDTO.setInStock(inStock);
            productDTO.setTitle(title);
            productDTO.setDescription(description);

            productDTOS.add(productDTO);
        }
        return productDTOS;
    }
}