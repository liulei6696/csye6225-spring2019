package edu.neu.coe.csye6225.service.impl;

import edu.neu.coe.csye6225.entity.Note;
import edu.neu.coe.csye6225.entity.User;
import edu.neu.coe.csye6225.mapper.UserMapper;
import edu.neu.coe.csye6225.service.AccountValidation;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AccountValidationImpl implements AccountValidation {

    @Autowired
    private UserMapper userMapper;

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

    public boolean isUserRegistered(User user) {
        User thisuser = userMapper.getUserByUsername(user.getUsername());
        if (thisuser == null) return false;
        else return true;

    }

    @Override
    public boolean isPasswordCorrect(User user) {
        User thisuser = userMapper.getUserByUsername(user.getUsername());
        if (BCrypt.checkpw(user.getPassword(), thisuser.getPassword()))
            return true;
        else
            return false;
    }

//    @Override
//    public boolean isNoteBelongToUser(Note note, User user) {
//        //TODO User.getId
//        if(note == null || user == null || note.getUserId() == null)
//            return false;
//        if (note.getUserId().equals(user.getId())) {
//            return true;
//        }
//        return false;
//    }
}
