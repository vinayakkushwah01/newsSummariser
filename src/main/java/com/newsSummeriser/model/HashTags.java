package com.newsSummeriser.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "hashtags")
@Data
public class HashTags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "news_id")
    private NewsHeadline newsHeadline; 
}
