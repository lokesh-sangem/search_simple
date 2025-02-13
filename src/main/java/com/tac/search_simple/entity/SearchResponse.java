package com.tac.search_simple.entity;


import lombok.Data;
import java.util.List;

@Data
public class SearchResponse<T> {
    private List<T> results;
    private int totalPages;
    private long totalElements;

    public SearchResponse(List<T> results, int totalPages, long totalElements) {
        this.results = results;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }
}

