package com.tac.search_simple.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;


import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Entity(name="jira_ticket_data")
@Data
public class JiraTicket {
    private String id;
    @Id
    private UUID uniqueId;
    private String summary;
    private String description;
    private String reporter;
    @JsonProperty("created_at")
    private ZonedDateTime createdAt;
    @JsonProperty("updated_at")
    private ZonedDateTime updatedAt;
    @ElementCollection
    private List<String> labels;

}


