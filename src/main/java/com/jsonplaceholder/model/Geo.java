package com.jsonplaceholder.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "geo")
public class Geo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String lat;

    @Column(nullable = false)
    private String lng;
} 