package com.example.a310frontend;

public class SImage {
    private int id;
    private String username;
    private String imagePath;

    public SImage(int id, String username, String imagePath) {
        this.id = id;
        this.username = username;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
