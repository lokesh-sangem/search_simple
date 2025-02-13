package com.tac.search_simple.entity;


import lombok.Data;
import java.time.ZonedDateTime;
import java.util.List;

@Data
public class JiraTicket {
    private String id;
    private String summary;
    private String description;
    private String reporter;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private List<String> labels;
}

