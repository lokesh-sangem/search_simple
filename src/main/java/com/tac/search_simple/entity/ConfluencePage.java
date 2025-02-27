package com.tac.search_simple.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity(name="confluence_page_data")
public class ConfluencePage {
    @Column(nullable = false,unique = true)
    private String id;
    @Id
    @Column(nullable = false,updatable = false)
    private UUID uniqueId;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
//    @JsonProperty("created_at")
    @Column(updatable=false,nullable=false)
    private ZonedDateTime createdAt;
//    @JsonProperty("updated_at")
//    @Column(nullable=false)
    private ZonedDateTime updatedAt;
      @ElementCollection
      @CollectionTable(name="confluence_page_tags",joinColumns=@JoinColumn(name="confluence_page_data_unique_id"))
      @Column(nullable = false)
    private List<String> tags;
      @ElementCollection
      @CollectionTable(name="confluence_page_links",joinColumns=@JoinColumn(name="confluence_page_data_unique_id"))
      @Column(nullable=false)
    private List<Link>links;

      @Embeddable
      @Data
      public static class Link{
          @Column(nullable=false)
          private String type;
          @Column(nullable=false)
          private String id;
          @Column(nullable=false)
          private String title;
          @Column(nullable=false)
          private String reporter;
      }

}


