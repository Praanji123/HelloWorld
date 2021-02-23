package com.example.SpringSession.dto;

public class SearchRequestdto {

    private String searchTerm;
    private String location;
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }


}
