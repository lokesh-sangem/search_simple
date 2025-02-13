package com.tac.search_simple.service;

import com.tac.search_simple.entity.ConfluencePage;
import com.tac.search_simple.entity.JiraTicket;
import com.tac.search_simple.entity.SearchResponse;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {
    private final DataLoaderService dataLoaderService;

    public SearchService(DataLoaderService dataLoaderService) {
        this.dataLoaderService = dataLoaderService;
    }

    public SearchResponse<?> search(String query, String type, int page, int size) throws IOException {
        if ("confluence".equalsIgnoreCase(type)) {
            return paginateResults(filterConfluencePages(dataLoaderService.loadConfluencePages(), query), page, size);
        } else if ("jira".equalsIgnoreCase(type)) {
            return paginateResults(filterJiraTickets(dataLoaderService.loadJiraTickets(), query), page, size);
        }
        return new SearchResponse<>(List.of(), 0, 0);
    }

    private List<ConfluencePage> filterConfluencePages(List<ConfluencePage> pages, String query) {
        return pages.stream()
                .filter(p -> p.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        p.getContent().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    private List<JiraTicket> filterJiraTickets(List<JiraTicket> tickets, String query) {
        return tickets.stream()
                .filter(t -> t.getSummary().toLowerCase().contains(query.toLowerCase()) ||
                        t.getDescription().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    private <T> SearchResponse<T> paginateResults(List<T> items, int page, int size) {
        int totalItems = items.size();
        int totalPages = (int) Math.ceil((double) totalItems / size);
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, totalItems);

        List<T> paginatedList = (fromIndex < totalItems) ? items.subList(fromIndex, toIndex) : List.of();
        return new SearchResponse<>(paginatedList, totalPages, totalItems);
    }
}

