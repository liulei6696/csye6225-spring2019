package edu.neu.coe.csye6225.service.impl;

import edu.neu.coe.csye6225.entity.User;
import edu.neu.coe.csye6225.mapper.UserMapper;
import edu.neu.coe.csye6225.service.AccountService;
import edu.neu.coe.csye6225.service.AccountValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AccountValidation accountValidation;

    // Log in
    public boolean logIn(User user) {
        if (user == null || user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            System.out.println("Username or password cannot be empty!");
            return false;
        } else {
            // User exists and password correct
            if (accountValidation.isUserRegistered(user)) {
                if (accountValidation.isPasswordCorrect(user)) {
                    System.out.println("User: '" + user.getUsername() + "' logged in!");
                    return true;
                } else {
                    System.out.println("Wrong username or password!");
                    return false;
                }

            } else {
                System.out.println("Wrong username or password!");
                return false;
            }
        }
    }

    public AccountServiceImpl() {
    }

    // Create a new account for user
    public boolean signUp(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Username or password cannot be empty!");
            return false;
        } else if (accountValidation.nameValidation(username)) {
            if (accountValidation.isUserRegistered(user)) {
                System.out.println("User already registered! Please sign in!");
                return false;
            } else {
                if (accountValidation.isPasswordStrong(password)) {
                    String saltedPwd = accountValidation.passwordEncrypt(password);
                    User saltedUser = new User(username, saltedPwd);
                    userMapper.insertUser(saltedUser);
                    System.out.println("User registration success!");
                    return true;
                } else {
                    System.out.println("Your password is not strong enough!");
                    return false;
                }

            }

        } else {
            System.out.println("Fail to sign up! Username should be email address!");
            return false;
        }

    }


}