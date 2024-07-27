package com.project.studentmanagement.model;

import java.util.logging.Level;
import java.util.logging.Logger;

public class User {
    private int sn;
    private String username;
    private String password; // Changed from hashedPassword to password
    private String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password; // Changed from hashedPassword to password
        this.role = role;
    }

    public User(int sn, String username, String password, String role) {
        this.sn = sn;
        this.username = username;
        this.password = password; // Changed from hashedPassword to password
        this.role = role;
    }

    private static final Logger logger = Logger.getLogger(User.class.getName());

    // Method to verify password
    public boolean verifyPassword(String inputPassword) {
        if (inputPassword == null || password == null) {
            logger.log(Level.SEVERE, "Password verification failed: input or stored password is null");
            return false;
        }
        return password.equals(inputPassword);
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

    public String getRole() {
        return role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String toCSVFormat() {
        return sn + "," + username + "," + password + "," + role;
    }
}
