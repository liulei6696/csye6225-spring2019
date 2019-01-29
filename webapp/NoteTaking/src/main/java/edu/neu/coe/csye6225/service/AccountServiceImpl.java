package edu.neu.coe.csye6225.service;

import edu.neu.coe.csye6225.entity.User;
import edu.neu.coe.csye6225.validation.AccountValidationImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AccountServiceImpl implements AccountService{
    private List<User> users = new ArrayList<>();

    // Log in
    public void logIn(String username, String password) {
        AccountValidationImpl asimpl = new AccountValidationImpl();
        if(username.isEmpty() || password.isEmpty()) {
            System.out.println("Username or password cannot be empty!");
        } else {


        }
    }

    public AccountServiceImpl(List<User> users) {
        this.users = users;
    }

    // Create a new account
    public void signUp(String username, String password){
        AccountValidationImpl asimpl = new AccountValidationImpl();
        if(username.isEmpty() || password.isEmpty()) {
            System.out.println("Username or password cannot be empty!");
        }
        else if(asimpl.nameValidation(username)) {
            User user = new User(username,password);
            if (isUserRegistered(user)){
                System.out.println("User already registered! Please sign in!");
            } else {
                if(asimpl.isPasswordStrong(password)) {
                    String saltedPwd = asimpl.passwordEncrypt(password);
                    User saltedUser = new User(username, saltedPwd);
                    // TODO； write into database
                    updateUser(saltedUser);
                } else {
                    System.out.println("Your password is not strong enough!");
                }

            }

        } else {
            System.out.println("Fail to sign up!");
        }

    }

    public boolean isUserRegistered(User user) {
        // TODO: Mapper.getAllUsers()

        List<User> allUsers = getAllUsers();
        if(allUsers.size() == 0 || allUsers == null) {
            return false;
        }
        Map<String, User> userMap = allUsers.stream().collect(
                Collectors.toMap(x -> x.getUsername(), x -> x));

        if (userMap.containsKey(user.getUsername())) {
            return true;
        }
        return false;
    }



    // Get user information from database (return a map)
    public List<User> getAllUsers() {
        return null;
    }

    public User getUserByUsername(String username) {
        return null;
    }

    // Update user information in database
    public void updateUser (User user) {

    }
}
