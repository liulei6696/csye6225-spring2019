package edu.neu.coe.csye6225.service;

import edu.neu.coe.csye6225.entity.User;

public interface AccountService {
    // User login
    boolean logIn(User user);

    // User registration
    boolean signUp(User user);


}
