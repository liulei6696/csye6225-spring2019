package edu.neu.coe.csye6225.service;

import edu.neu.coe.csye6225.entity.User;

import java.util.List;

public interface AccountService {
    void logIn(String username, String password);

    // Create a new account
    void signUp(String username, String password);

    // Get the list of all users from database
    List<User> getAllUsers();

    // Get user by username (read)
    User getUserByUsername(String username);

    // Update user information in database (write)
    void updateUser (User user);
}
