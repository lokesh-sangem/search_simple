package com.tac.search_simple.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name="confluence_page_links")
public class Link {
    @Id
    private UUID linkId;
  @Column(nullable =false)
  private String type;
  @Column(nullable=false)
  private String id;
//  private String linkReferenceId;
  @Column(nullable=false)
  private String title;
  @Column(nullable =false)
  private String reporter;
  @ManyToOne
  @JoinColumn(name = "confluence_page_data_unique_id", nullable = false)
  private ConfluencePage confluencePage;
//  @PrePersist
//  public void generateId() {
//    if (this.linkId == null) {
//      this.linkId = UUID.randomUUID();
//    }
//  }
}


