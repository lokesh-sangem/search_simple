package com.tac.search_simple.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tac.search_simple.entity.ConfluencePage;
import com.tac.search_simple.entity.JiraTicket;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataLoaderService {
    private final ObjectMapper objectMapper;

    public List<JiraTicket> loadJiraTickets() throws IOException {
        JsonNode rootNode = objectMapper.readTree(new ClassPathResource("updated_mock_data.json").getInputStream());
        List<JiraTicket> tickets = new ArrayList<>();
        for (JsonNode node : rootNode.get("jira_tickets").get("tickets")) {
            tickets.add(objectMapper.treeToValue(node, JiraTicket.class));
        }
        return tickets;
    }

    public List<ConfluencePage> loadConfluencePages() throws IOException {
        JsonNode rootNode = objectMapper.readTree(new ClassPathResource("updated_mock_data.json").getInputStream());
        List<ConfluencePage> pages = new ArrayList<>();
        for (JsonNode node : rootNode.get("confluence_pages").get("pages")) {
            pages.add(objectMapper.treeToValue(node, ConfluencePage.class));
        }
        return pages;
    }
}
