package com.example.SpringSession.dto;

import java.util.List;

public class SearchResponseDto {


    private List<ProductDTO> products;
    private List<ProductDTO> locationBasedProd;
    public List<ProductDTO> getLocationBasedProd() {
        return locationBasedProd;
    }

    public void setLocationBasedProd(List<ProductDTO> locationBasedProd) {
        this.locationBasedProd = locationBasedProd;
    }


    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }




}
