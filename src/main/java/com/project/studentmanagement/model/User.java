package com.project.studentmanagement.model;

public class User {
    private int sn;
    private String username;
    private String password;
    private String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(int sn, String username, String password, String role) {
        this.sn = sn;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String toCSVFormat() {
        return sn + "," + username + "," + password + "," + role;
    }
}
