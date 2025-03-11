package com.newsSummeriser.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"breaking_headline", "breaking_url"})
})
public class BreakingNews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tId;

    @Column(name = "breaking_headline", nullable = false)
    private String breakingHeadline;

    @Column(name = "breaking_url")
    private String breakingUrl;

    @Column(name = "breaking_time")  // Renamed column
    private String breakingTime;
    private LocalDateTime localDateTime;
    
}
