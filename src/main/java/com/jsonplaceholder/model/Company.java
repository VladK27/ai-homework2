package com.jsonplaceholder.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "catch_phrase", nullable = false)
    private String catchPhrase;

    @Column(nullable = false)
    private String bs;
} 