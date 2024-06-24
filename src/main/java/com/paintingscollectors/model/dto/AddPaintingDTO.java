package com.paintingscollectors.model.dto;

import com.paintingscollectors.model.entity.StyleName;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddPaintingDTO {

    @NotNull
    @Size(min = 5, max = 40)
    private String name;

    @NotNull
    @Size(min = 5, max = 30)
    private String author;

    @NotNull
    @Size(min = 1, max = 150)
    private String imageUrl;

    @NotNull
    private StyleName style;

    public AddPaintingDTO() {
    }

    public AddPaintingDTO(String name, String author, String imageUrl, StyleName style) {
        this.name = name;
        this.author = author;
        this.imageUrl = imageUrl;
        this.style = style;
    }

    public @NotNull @Size(min = 5, max = 40) String getName() {
        return name;
    }

    public void setName(@NotNull @Size(min = 5, max = 40) String name) {
        this.name = name;
    }

    public @NotNull @Size(min = 5, max = 30) String getAuthor() {
        return author;
    }

    public void setAuthor(@NotNull @Size(min = 5, max = 30) String author) {
        this.author = author;
    }

    public @NotNull @Max(150) String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@NotNull @Size(min = 1, max = 150) String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public @NotNull StyleName getStyle() {
        return style;
    }

    public void setStyle(@NotNull StyleName style) {
        this.style = style;
    }
}
