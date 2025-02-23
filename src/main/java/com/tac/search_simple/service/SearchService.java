package com.tac.search_simple.service;

import com.tac.search_simple.dto.ConfluencePageDTO;
import com.tac.search_simple.dto.JiraTicketDTO;
import com.tac.search_simple.entity.ConfluencePage;
import com.tac.search_simple.entity.JiraTicket;
import com.tac.search_simple.entity.SearchResponse;
import com.tac.search_simple.mappers.ConfluencePageMapper;
import com.tac.search_simple.mappers.JiraTicketMapper;
import com.tac.search_simple.repo.ConfluencePageRepo;
import com.tac.search_simple.repo.JiraTicketRepo;
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
    private final ConfluencePageRepo confluencePageRepo;
    private final JiraTicketRepo jiraTicketRepo;

    public SearchService(DataLoaderService dataLoaderService,ConfluencePageRepo confluencePageRepo,JiraTicketRepo jiraTicketRepo) {
        this.dataLoaderService = dataLoaderService;
        this.confluencePageRepo = confluencePageRepo;
        this.jiraTicketRepo = jiraTicketRepo;
    }

    public SearchResponse<?> search(String query, String type, int page, int size) throws IOException {
        if ("confluence".equalsIgnoreCase(type)) {
            List<ConfluencePageDTO> confluencePageDTOList =confluencePageRepo.findAll().stream().map((confluencePage)-> ConfluencePageMapper.toDTO(confluencePage)).collect(Collectors.toList());
            return paginateResults(filterConfluencePages(confluencePageDTOList, query), page, size);
        } else if ("jira".equalsIgnoreCase(type)) {
           List<JiraTicketDTO>jiraTicketDTOList= jiraTicketRepo.findAll().stream().map((jiraTicket)-> JiraTicketMapper.toDTO(jiraTicket)).collect(Collectors.toList());
            return paginateResults(filterJiraTickets(jiraTicketDTOList, query), page, size);
        }
        return new SearchResponse<>(List.of(), 0, 0);
    }



    private List<ConfluencePageDTO> filterConfluencePages(List<ConfluencePageDTO> pages, String query) {
        return pages.stream()
                .filter(p -> p.getId().toLowerCase().contains(query.toLowerCase()) ||
                        p.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        p.getContent().toLowerCase().contains(query.toLowerCase()) ||
//                        p.getTags().stream().anyMatch(tag -> tag.toLowerCase().contains(query.toLowerCase())) ||
                        p.getDescription().toLowerCase().contains(query.toLowerCase())
                )
                .collect(Collectors.toList());
    }

    private List<JiraTicketDTO> filterJiraTickets(List<JiraTicketDTO> tickets, String query) {
        return tickets.stream()
                .filter(t -> t.getId().toLowerCase().contains(query.toLowerCase()) ||
                        t.getSummary().toLowerCase().contains(query.toLowerCase()) ||
                        t.getDescription().toLowerCase().contains(query.toLowerCase()) ||
                        t.getReporter().toLowerCase().contains(query.toLowerCase())
                        //t.getLabels().stream().anyMatch(label -> label.toLowerCase().contains(query.toLowerCase())
                        )
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

