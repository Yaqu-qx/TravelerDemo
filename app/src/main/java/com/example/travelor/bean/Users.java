package com.example.travelor.bean;

import java.io.Serializable;

public class Users implements Serializable {

    private String account;
    private String password;
    private String username;
    private String createdTime;
    private String id;

    public void setUserName(String userName) {
        this.username = userName;
    }

    public String getUserName() {
        return username;
    }

    public String getAccount() {
        return account;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Users{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", userName='" + username + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
