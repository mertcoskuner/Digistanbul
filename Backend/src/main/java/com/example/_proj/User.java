package com.example._proj;

import java.util.List;

import com.example._proj.Place;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Data
@Document
public class User {
    @Id
    private String id;
    private String username;

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Place> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Place> favorites) {
        this.favorites = favorites;
    }

    public Place getFavPlace() {
        return favPlace;
    }

    public void setFavPlace(Place favPlace) {
        this.favPlace = favPlace;
    }

    @Indexed(unique = true)
    private String email;
    private String password;
    private String phoneNumber;

    private List <Place> favorites;
    private Place favPlace;

    public User(String username, String email, String password, String phoneNumber) {
        super();
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public User() {}
}