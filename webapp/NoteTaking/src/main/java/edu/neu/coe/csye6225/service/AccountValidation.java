package edu.neu.coe.csye6225.service;

import edu.neu.coe.csye6225.entity.User;

public interface AccountValidation {
    // Username validation
    boolean nameValidation(String username);

    // Validate password to be strong
    boolean isPasswordStrong(String password);

    // Password encryption
    String passwordEncrypt(String password);

    // Check user exists or not
    boolean isUserRegistered (User user);

    // Check user password correct or not
    boolean isPasswordCorrect(User user);
}
