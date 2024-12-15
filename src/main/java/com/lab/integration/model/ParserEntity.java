package com.lab.integration.model;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.repository.cdi.Eager;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class ParserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column
    private String useHeaderDelimiters;

    @Column
    private String componentDelimiter;

    @Column
    private String subComponentDelimiter;

    @Column
    private String escapeCharacter;

    @Column
    private String repetitionDelimiter;


    @Column(name = "segment_delimiter", nullable = false, length = 50)
    private String segmentDelimiter;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "parser", cascade = CascadeType.ALL, orphanRemoval = true , fetch = FetchType.EAGER)
    private List<Segment> segments;




}
