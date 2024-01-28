package com.example._proj;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public Optional<User> deleteUsername(String username) {
        return userRepository.deleteUserByUsername(username);
    }

    public User registerUser(User user) {
        // Validate if the username or email is already taken before saving (you can add this logic)

        // Hash the user's password before saving it.
        user.setPassword(user.getPassword());

        return userRepository.save(user);
    }

    public boolean loginUser(String username, String password) {
        // Implement your logic to check if the username and password match an existing user
        User user = userRepository.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            return true; // Successful login
        }

        return false; // Invalid credentials
    }
}