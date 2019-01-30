package edu.neu.coe.csye6225.service;

import edu.neu.coe.csye6225.entity.User;
import edu.neu.coe.csye6225.mapper.UserMapper;
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
        if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            System.out.println("Username or password cannot be empty!");
            return false;
        } else {
            // User exists and password correct
            if (accountValidation.isUserRegistered(user)) {
                System.out.println("User: '" + user.getUsername() + "' exists!");
                if (accountValidation.isPasswordCorrect(user)) {
                    System.out.println("User: '" + user.getUsername() + "' logged in!");
                    return true;
                } else {
                    System.out.println("Wrong Password!");
                    return false;
                }

            } else {
                System.out.println("User does not exists!");
                return false;
            }
        }
    }

    public AccountServiceImpl() {
    }

    // Create a new account for user
    public void signUp(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Username or password cannot be empty!");
        } else if (accountValidation.nameValidation(username)) {
            if (accountValidation.isUserRegistered(user)) {
                System.out.println("User already registered! Please sign in!");
            } else {
                if (accountValidation.isPasswordStrong(password)) {
                    String saltedPwd = accountValidation.passwordEncrypt(password);
                    User saltedUser = new User(username, saltedPwd);
                    userMapper.insertUser(saltedUser);
                    System.out.println("User registration success!");
                } else {
                    System.out.println("Your password is not strong enough!");
                }

            }

        } else {
            System.out.println("Fail to sign up! Username should be email address!");
        }

    }


}
