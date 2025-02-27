package com.tac.search_simple.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class JiraTicketDTO {
        private String id;
        private String summary;
        private String description;
        private String reporter;
        @JsonProperty("created_at")
        private ZonedDateTime createdAt;
        @JsonProperty("updated_at")
        private ZonedDateTime updatedAt;
        private List<String>labels;
}

