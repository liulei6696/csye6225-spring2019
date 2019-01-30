package edu.neu.coe.csye6225.service;

import edu.neu.coe.csye6225.entity.User;
import edu.neu.coe.csye6225.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private UserMapper userMapper;

    // Log in
    public void logIn(User user) {
        AccountValidationImpl asimpl = new AccountValidationImpl();
        if(user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            System.out.println("Username or password cannot be empty!");
        } else {
            // TODO basic authentication
            System.out.println(user.getUsername() + ":/" + user.getPassword());
        }
    }

    public AccountServiceImpl() {
    }

    // Create a new account
    public void signUp(String username, String password){
        AccountValidationImpl asimpl = new AccountValidationImpl();
        if(username.isEmpty() || password.isEmpty()) {
            System.out.println("Username or password cannot be empty!");
        }
        else if(asimpl.nameValidation(username)) {
            if (isUserRegistered(username)){
                System.out.println("User already registered! Please sign in!");
            } else {
                if(asimpl.isPasswordStrong(password)) {
                    String saltedPwd = asimpl.passwordEncrypt(password);
                    User saltedUser = new User(username, saltedPwd);
                    // TODO； userMapper.insertUser
                    userMapper.insertUser(saltedUser);
                } else {
                    System.out.println("Your password is not strong enough!");
                }

            }

        } else {
            System.out.println("Fail to sign up!");
        }

    }

    @Override
    public User getUser(String u, String p) {
        return new User(u,p);
    }

    public boolean isUserRegistered(String username) {
        //TODO userMapper.selectByUsername
        User user = userMapper.getUserByUsername(username);
        if(user == null) return false;
        else return true;

/*        List<User> allUsers = userMapper.getAllUsers();
        if(allUsers.size() == 0 || allUsers == null) {
            return false;
        }
        Map<String, User> userMap = allUsers.stream().collect(
                Collectors.toMap(x -> x.getUsername(), x -> x));

        if (userMap.containsKey(user.getUsername())) {
            return true;
        }
        return false;*/
    }


}
