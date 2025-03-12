package com.newsSummeriser.model;

import jakarta.persistence.*;

@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String url;

    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false)
    private State state;

    // Constructors, Getters, Setters
    public City() {}

    public City(String name, String url, State state) {
        this.name = name;
        this.url = url;
        this.state = state;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public State getState() { return state; }
    public void setState(State state) { this.state = state; }
}

