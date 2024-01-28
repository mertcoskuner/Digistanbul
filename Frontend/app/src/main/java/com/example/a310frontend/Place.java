package com.example.a310frontend;

import android.os.Parcel;
import android.os.Parcelable;

public class Place implements Parcelable {


    private String id;
    private String placeName;
    private String placeType;
    private String imagePath;
    private String description;
    private double rating;
    private double price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Place(String id, String placeName, String placeType, String imagePath,
                 String description, double price, double rating) {
        this.id = id;
        this.placeName = placeName;
        this.placeType = placeType;
        this.imagePath = imagePath;
        this.description = description;
        this.price = price;
        this.rating = rating;
    }

    public Place() {

    }

    protected Place(Parcel in) {
        id = in.readString();
        placeName = in.readString();
        description = in.readString();
        imagePath = in.readString();
        placeType = in.readString();
        rating = in.readDouble();
        price = in.readDouble();
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // Write data to the parcel
        dest.writeString(id);
        dest.writeString(placeName);
        dest.writeString(placeType);
        dest.writeString(description);
        dest.writeString(imagePath);
        dest.writeDouble(price);
        dest.writeDouble(rating);
    }

}