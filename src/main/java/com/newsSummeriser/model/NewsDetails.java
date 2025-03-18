package com.newsSummeriser.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "news_details")
public class NewsDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String headline;

    @Column(nullable = false)
    private String imageUrl;

    @Lob
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String detailedNews;

    @OneToOne
    @JoinColumn(name = "news_headline_id", nullable = false)
    @JsonBackReference
    private NewsHeadline newsHeadline;

    public NewsDetails() {}
}
