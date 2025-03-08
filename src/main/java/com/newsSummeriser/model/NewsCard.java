package com.newsSummeriser.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
@Data
@Entity
@Table( uniqueConstraints = {
    @UniqueConstraint(columnNames = {"headline", "image_url" ,"article_link"})
})
public class NewsCard{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id")
    private Long id;

    // 
    // private String headline;

    // @Column(name = "url", nullable = false, length = 1024)
    // private String url;

    // @Column(name = "image_url", length = 1024)
    // private String imageUrl;
    
    // @Column(name = "top_section", length = 1024)
    // private String  topSection;
    // // Default constructor (required by JPA)

    // private LocalDateTime date;
    @Column(name = "headline", nullable = false, length = 512 )
    private String headline;

    private String subheading;
    @Column(name = "article_link", nullable = false, length = 1024 )
    private String articleLink;
    @Column(name = "image_url", length = 1024)
    private String imageUrl;

    @OneToMany(mappedBy = "newsCard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tags> tags;

    @OneToMany(mappedBy = "newsCard", cascade = CascadeType.ALL, orphanRemoval = true)
     private List<HashTags> hashtags;
    private String date;
    public NewsCard() {
    }
}
