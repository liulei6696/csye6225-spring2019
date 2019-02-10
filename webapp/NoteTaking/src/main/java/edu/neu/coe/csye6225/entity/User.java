package edu.neu.coe.csye6225.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User implements Serializable {


    private String userId;

    private String username;

    private String password;

    private List<Note>notes = new ArrayList<>();

    public void setUserId() {
        this.userId = UUID.randomUUID().toString();
    }

    public String getUserId() {
        return userId;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public User(String username, String password) {
        setUserId();
        this.username = username;
        this.password = password;
    }


    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", notes=" + notes +
                '}';
    }

    public User() {

    }


}
