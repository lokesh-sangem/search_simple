package com.tac.search_simple.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name="confluence_page_tags")
public class Tag {
    @Id
    @Column(nullable=false,updatable=false)
//    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID tagId;
    @Column(nullable =false)
    private String tag;
    @ManyToOne
    @JoinColumn(name="confluence_page_data_unique_id",nullable=false)
    private ConfluencePage confluencePage;

}

