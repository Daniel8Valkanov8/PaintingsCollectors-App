package com.paintingscollectors.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table( name = "styles" )
@AllArgsConstructor
@Getter
@Setter
public class Style {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private StyleName name;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "style", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Painting> paintings;

    public Style() {
        this.paintings = new HashSet<>();
    }

    public Style(StyleName name, String description) {
        this();
        this.name = name;
        this.description = description;
    }
}