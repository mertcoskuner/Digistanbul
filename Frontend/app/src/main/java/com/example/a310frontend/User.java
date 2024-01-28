package com.example.a310frontend;

public class User {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    private String username;
    private String email;
    private String password;
    private String favorites;
    private String favPlace;
    private String imagePath;

    public User(String username, String email, String password, String favorites, String favPlace) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.favorites = favorites;
        this.favPlace = favPlace;
    }

    // Getters and setters for all the fields

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFavorites() {
        return favorites;
    }

    public void setFavorites(String favorites) {
        this.favorites = favorites;
    }

    public String getFavPlace() {
        return favPlace;
    }

    public void setFavPlace(String favPlace) {
        this.favPlace = favPlace;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
