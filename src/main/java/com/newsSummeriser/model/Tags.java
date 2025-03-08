package com.newsSummeriser.model;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "tags")
@Data
public class Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "news_id")  // Must match NewsCard's primary key
    private NewsCard newsCard; 
}
