package edu.neu.coe.csye6225.service;

public interface AccountValidation {
    // Username validation
    boolean nameValidation(String username);

    // Validate password to be strong
    public boolean isPasswordStrong(String password);

    // Password encryption
    public String passwordEncrypt(String password);
}
