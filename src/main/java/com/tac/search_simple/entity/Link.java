package com.tac.search_simple.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Link {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
    private UUID linkId;
  @Column()
}
