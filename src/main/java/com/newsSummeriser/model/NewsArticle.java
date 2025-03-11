package com.newsSummeriser.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "news_articles")
public class NewsArticle {
    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String headline;

    @Column(nullable = false)
    private String imageUrl;

    @Lob
    @Column(nullable = false, columnDefinition = "LONGTEXT ")
    private String detailedNews;

    // // Constructors
    // public NewsArticle() {}

    // public NewsArticle(String headline, String imageUrl, String detailedNews) {
    //     this.headline = headline;
    //     this.imageUrl = imageUrl;
    //     this.detailedNews = detailedNews;
    // }

    // // Getters and Setters
    // public Long getId() { return id; }
    // public String getHeadline() { return headline; }
    // public String getImageUrl() { return imageUrl; }
    // public String getDetailedNews() { return detailedNews; }

    // public void setId(Long id) { this.id = id; }
    // public void setHeadline(String headline) { this.headline = headline; }
    // public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    // public void setDetailedNews(String detailedNews) { this.detailedNews = detailedNews; }

    // @Override
    // public String toString() {
    //     return "Headline: " + headline + "\nImage URL: " + imageUrl + "\n\nDetailed News:\n" + detailedNews;
    // }
}

