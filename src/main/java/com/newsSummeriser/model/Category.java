package com.newsSummeriser.model;

import java.util.List;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false, unique = true, length = 255)
    private String name;
    
    @Column(nullable = false)
    private String url;

    // @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<NewsHeadline> newsHeadlines;

    public Category() {}

    public Category(String name) {
        this.name = name;
    }
}
