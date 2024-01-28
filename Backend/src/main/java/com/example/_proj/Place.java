package com.example._proj;

import com.example._proj.Comment;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.List;

@Data
public class Place {

    @Id
    private String id;

    public Place(String placeName, String placeType, double rating, String imagePath, String description, String facilities, double price, String address, int rateCount, List<Comment> comments) {
        this.placeName = placeName;
        this.placeType = placeType;
        this.rating = rating;
        this.imagePath = imagePath;
        this.description = description;
        this.facilities = facilities;
        this.price = price;
        this.address = address;
        this.rateCount = rateCount;
        this.comments = comments;
    }

    private String placeName;
    private String placeType;
    private double rating;
    private String imagePath;
    private String description;
    private String facilities;
    private double price;
    private String address;
    private int rateCount;
    private List<Comment> comments;

}