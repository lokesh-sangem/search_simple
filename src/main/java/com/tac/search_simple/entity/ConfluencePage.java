package com.tac.search_simple.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity(name="confluence_page_data")
public class ConfluencePage {
    private String id;
    @Id
    private UUID uniqueId;
    private String description;
    private String title;
    private String content;
//    @JsonProperty("created_at")
    @Column(updatable=false)
    private ZonedDateTime createdAt;
//    @JsonProperty("updated_at")
    private ZonedDateTime updatedAt;
//    private List<String> tags;

}


