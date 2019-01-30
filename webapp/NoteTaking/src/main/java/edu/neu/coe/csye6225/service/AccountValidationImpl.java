package edu.neu.coe.csye6225.service;

import org.mindrot.jbcrypt.BCrypt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountValidationImpl implements AccountValidation{
    public AccountValidationImpl() {
    }

    // Username validation
    public boolean nameValidation(String username) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(username);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
            System.out.println("Username must be a valid email address!");
        }
        return flag;
    }

    // Validate password to be strong
    public boolean isPasswordStrong(String password) {
        if (password.matches("^.*[a-zA-Z]+.*$") && password.matches("^.*[0-9]+.*$")
                && password.matches("^.*[/^/$/.//,;:'!@#%&/*/|/?/+/(/)/[/]/{/}]+.*$")   // contain numbers, letters and symbols
                && password.matches("^.{8,}$")             // at least 8 digits
                && !password.matches("^.*[\\s]+.*$")       // cannot contain whitespace characters
                && !password.matches("^.*(.)\\1{2,}+.*$")  // duplicates of the same characters < 3
        ) return true;
        else
            return false;
    }

    // Password encryption
    public String passwordEncrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }
}
