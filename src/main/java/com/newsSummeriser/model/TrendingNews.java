package com.newsSummeriser.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

// import jakarta.persistence.*;
// import lombok.Data;

@Data
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"trending_headline", "trending_imageUrl" ,"trending_url"})
})
public class TrendingNews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tId;

    @Column(name = "trending_headline", nullable = false)
    private String trendingHeadline;

    @Column(name = "trending_imageUrl", nullable = false)
    private String trendingImageUrl;

    @Column(name = "trending_url")
    private String trendingUrl;

    @Column(name = "trending_category")
    private String trendingCategory;

    @Column(name = "trending_date")
    private String trendingDate;

    // Custom constructor
    public TrendingNews(String headline, String imageUrl, String category, String date, String articleUrl) {
        this.trendingHeadline = headline;
        this.trendingImageUrl = imageUrl;
        this.trendingCategory = category;
        this.trendingDate = date;
        this.trendingUrl = articleUrl;
        System.out.println("Constructor called In T news");
    }
}
