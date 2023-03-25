package com.example.shoesstore.models;

public class Category {
    private String imageUrl;
    private String name;

    public Category() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category(String imageUrl, String name) {
        this.imageUrl = imageUrl;
        this.name = name;
    }
}
