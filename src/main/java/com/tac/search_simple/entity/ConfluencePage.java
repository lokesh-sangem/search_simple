package com.tac.search_simple.entity;

import lombok.Data;
import java.time.ZonedDateTime;
import java.util.List;

@Data
public class ConfluencePage {
    private String id;
    private String title;
    private String content;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private List<String> tags;
}


