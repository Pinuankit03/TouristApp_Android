package com.example.travelapp.Model;

import java.io.Serializable;

public class UserData implements Serializable {

    private int userId;
    private String username;
    private String password;

    private boolean rememberUser;

    public UserData(int userId, String username, String password, boolean rememberUser) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.rememberUser = rememberUser;
    }

    public UserData() {
    }

    @Override
    public String toString() {
        return "UserData{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", rememberUser=" + rememberUser +
                '}';
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isRememberUser() {
        return rememberUser;
    }

    public void setRememberUser(boolean rememberUser) {
        this.rememberUser = rememberUser;
    }
}
