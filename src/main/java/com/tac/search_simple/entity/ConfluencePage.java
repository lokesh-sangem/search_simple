package com.tac.search_simple.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity(name="confluence_page_data")
public class ConfluencePage {
//    @Column(nullable = false)
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
    @Column(updatable=false,nullable = false)
    private ZonedDateTime createdAt;
//    @JsonProperty("updated_at")
//    @Column(nullable=false)
    private ZonedDateTime updatedAt;
//      @ElementCollection
//      @CollectionTable(name="confluence_page_tags",joinColumns=@JoinColumn(name="confluence_page_data_unique_id"))
    @OneToMany(mappedBy ="confluencePage",cascade=CascadeType.ALL,orphanRemoval=true)
//      @Column(nullable = false)
    private List<Tag> tags = new ArrayList<>();
//      @ElementCollection
//      @CollectionTable(name="confluence_page_links",joinColumns=@JoinColumn(name="confluence_page_data_unique_id"))
    @OneToMany(mappedBy="confluencePage",cascade=CascadeType.ALL,orphanRemoval =true)
//      @Column(nullable=false)
    private List<Link>links = new ArrayList<>();

    // Utility methods for maintaining bidirectional relationship
    public void addTag(Tag tag) {
        tags.add(tag);
        tag.setConfluencePage(this);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
        tag.setConfluencePage(null);
    }

    public void addLink(Link link) {
        links.add(link);
        link.setConfluencePage(this);
    }

    public void removeLink(Link link) {
        links.remove(link);
        link.setConfluencePage(null);
    }


//      @Embeddable
//      @Data
//      public static class Link{
//          @Column(nullable=false)
//          private String type;
//          @Column(nullable=false)
//          private String id;
//          @Column(nullable=false)
//          private String title;
//          @Column(nullable=false)
//          private String reporter;
//      }

}


