package edu.neu.coe.csye6225.service.impl;

import edu.neu.coe.csye6225.entity.User;
import edu.neu.coe.csye6225.mapper.UserMapper;
import edu.neu.coe.csye6225.service.AccountService;
import edu.neu.coe.csye6225.service.AccountValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AccountValidation accountValidation;
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    // Log in
    public boolean logIn(User user) {
        if (user == null || user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            logger.info("Username or password is empty!");
            return false;
        } else {
            // User exists and password correct
            if (accountValidation.isUserRegistered(user.getUsername())) {
                if (accountValidation.isPasswordCorrect(user)) {
                    logger.info("User logged in!");
                    return true;
                } else {
                    logger.info("Wrong username or password!");
                    return false;
                }

            } else {
                logger.info("Wrong username or password!");
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
            logger.info("Username or password cannot be empty!");
            return false;
        } else if (accountValidation.nameValidation(username)) {
            if (accountValidation.isUserRegistered(user.getUsername())) {
                logger.info("User already registered! Please sign in!");
                return false;
            } else {
                if (accountValidation.isPasswordStrong(password)) {
                    String saltedPwd = accountValidation.passwordEncrypt(password);
                    User saltedUser = new User(username, saltedPwd);
                    userMapper.insertUser(saltedUser);
                    logger.info("User registration success!");
                    return true;
                } else {
                    logger.info("Your password is not strong enough!");
                    return false;
                }

            }

        } else {
            logger.info("Fail to sign up! Username should be email address!");
            return false;
        }

    }

    // create table
    public void createTable () {
        try {
            this.userMapper.createNewTable();
        } catch (Exception e) {
            logger.info("create again");
        }
    }


}
