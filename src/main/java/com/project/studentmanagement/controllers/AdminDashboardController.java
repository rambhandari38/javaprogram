package com.project.studentmanagement.controllers;

import com.project.studentmanagement.Main;
import com.project.studentmanagement.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.List;

public class AdminDashboardController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label roleLabel;

    @FXML
    private TextField newUserField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private TextField newRoleField;

    @FXML
    private Button addUserButton;

    private Main mainApp; // Ensure this is properly initialized

    private List<User> users; // Maintain a list of users

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setLoggedInUser(User user) {
        welcomeLabel.setText("Welcome, " + user.getUsername() + "!");
        roleLabel.setText("Role: " + user.getRole());

        // Load existing users from file or initialize a new list
        this.users = mainApp.getUsers();
        if (user.getRole().equals("Admin")) {
            newUserField.setVisible(true);
            newPasswordField.setVisible(true);
            newRoleField.setVisible(true);
            addUserButton.setVisible(true);
        }
    }

    @FXML
    private void addUser() {
        String newUsername = newUserField.getText();
        String newPassword = newPasswordField.getText();
        String newRole = newRoleField.getText();

        if (newUsername.isEmpty() || newPassword.isEmpty() || newRole.isEmpty()) {
            mainApp.showAlert("Error", "All fields are required to add a new user.", Alert.AlertType.ERROR);
            return;
        }

        // Check if user with the same username already exists
        for (User user : users) {
            if (user.getUsername().equals(newUsername)) {
                mainApp.showAlert("Error", "User with this username already exists.", Alert.AlertType.ERROR);
                return;
            }
        }

        // Create new user and add to list
        User newUser = new User(newUsername, newPassword, newRole);
        mainApp.addUserToFile(newUser); // Add user to credentials.txt

        mainApp.showAlert("Success", "User added successfully!", Alert.AlertType.INFORMATION);

        newUserField.clear();
        newPasswordField.clear();
        newRoleField.clear();
    }

    @FXML
    private void handleLogout() {
        if (mainApp != null) {
            mainApp.showLoginScreen();
        } else {
            System.out.println("MainApp is null, cannot logout.");
        }
    }
}
