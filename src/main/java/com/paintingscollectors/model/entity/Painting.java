package com.paintingscollectors.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "paintings")
@AllArgsConstructor
@Getter
@Setter
public class Painting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String author;

    @ManyToOne(optional = false)
    //@JoinColumn(name = "style_id")
    private Style style;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    //@JoinColumn(name = "owner_id" )
    private User owner;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private boolean isFavorite;

    @Column(nullable = false)
    private int votes;

    @ManyToMany(mappedBy = "favoritePaintings")
    private Set<User> favoritedByUsers;

    @ManyToMany(mappedBy = "ratedPaintings")
    private Set<User> ratedByUsers;

    public Painting() {
        this.favoritedByUsers = new HashSet<>();
        this.ratedByUsers = new HashSet<>();
    }
}
