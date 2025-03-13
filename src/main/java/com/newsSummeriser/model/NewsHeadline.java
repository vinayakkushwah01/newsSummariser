package com.newsSummeriser.model;

import java.util.List;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"headline", "image_url", "article_link"})
})
public class NewsHeadline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id")
    private Long id;

    @Column(name = "headline", nullable = false, length = 512)
    private String headline;

    private String subheading;

    @Column(name = "article_link", nullable = false, length = 1024)
    private String articleLink;

    @Column(name = "image_url", length = 1024)
    private String imageUrl;

    private String date;
    private boolean fetched;

     @OneToMany(mappedBy = "newsHeadline", cascade = CascadeType.ALL, orphanRemoval = true)
     private List<Tags> tags;

     @OneToMany(mappedBy = "newsHeadline", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HashTags> hashtags;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToOne(mappedBy = "newsHeadline", cascade = CascadeType.ALL)
    private NewsDetails newsDetails;

    public NewsHeadline() {}
}
