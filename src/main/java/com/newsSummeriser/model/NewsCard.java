package com.newsSummeriser.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name = "news_cards")
public class NewsCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "headline", nullable = false, length = 512)
    private String headline;

    @Column(name = "url", nullable = false, length = 1024)
    private String url;

    @Column(name = "image_url", length = 1024)
    private String imageUrl;
    
    @Column(name = "top_section", length = 1024)
    private String  topSection;

    // Default constructor (required by JPA)
    public NewsCard() {
    }
}
