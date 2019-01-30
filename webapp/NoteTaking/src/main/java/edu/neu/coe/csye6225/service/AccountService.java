package edu.neu.coe.csye6225.service;

import edu.neu.coe.csye6225.entity.User;

import java.util.List;

public interface AccountService {
    boolean logIn(User user);

    // Create a new account
    void signUp(String username, String password);

    User getUser(String u, String p);





}
