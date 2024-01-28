package com.example.a310frontend;

public class UserProfileRequest {
    private String email;
    private String username;
    private String oldPassword;
    private String newPassword;

    public UserProfileRequest() {
        // Default constructor
    }

    public UserProfileRequest(String email, String username, String oldPassword, String newPassword) {
        this.email = email;
        this.username = username;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    // Getters and setters for the fields
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}