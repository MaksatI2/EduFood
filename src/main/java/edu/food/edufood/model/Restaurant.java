package edu.food.edufood.model;


import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "restaurants")
@Getter
@Setter
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @JoinColumn(name = "photo_url", nullable = false)
    public String photoUrl;

    @OneToMany(mappedBy = "restaurant")
    private Set<Dishes> dishes;
}
