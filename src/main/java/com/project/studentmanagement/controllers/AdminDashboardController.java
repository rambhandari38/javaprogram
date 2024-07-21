package com.project.studentmanagement.controllers;

import com.project.studentmanagement.Main;
import com.project.studentmanagement.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.collections.ObservableList;

//import java.io.*;


public class AdminDashboardController {
    @FXML
    private AnchorPane mainDashboard;
    @FXML
    private AnchorPane studentDashboard;
    @FXML
    private AnchorPane teacherDashboard;
    @FXML
    private AnchorPane createAccountDashboard;
    @FXML
    private AnchorPane addStudentAdminDashboard;
    @FXML
    private AnchorPane viewStudentAdminDashboard;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField roleField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, Integer> snColumn;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> passwordColumn;

    @FXML
    private TableColumn<User, String> roleColumn;
    private ObservableList<User> userList;

    private List<User> users = new ArrayList<>();
    private static final String CSV_FILE = "users.csv";
    private static final AtomicInteger counter = new AtomicInteger(0);


    private Main mainApp;

    //    ----------------------------------------
    private Stage stage;
    private double xOffset = 0;
    private double yOffset = 0;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    protected void handleMinimizeButtonAction() {
        if (stage != null) {
            stage.setIconified(true);
        }
    }

    @FXML
    protected void handleCloseButtonAction() {
        if (stage != null) {
            stage.close();
        }
    }

    @FXML
    protected void handleTitleBarPressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    protected void handleTitleBarDragged(MouseEvent event) {
        if (stage != null) {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        }
    }
//    -----------------------------------------------------------------

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void showAddStudentDashboard() {
        addStudentAdminDashboard.setVisible(true);
        viewStudentAdminDashboard.setVisible(false);
    }

    @FXML
    private void showViewStudentDashboard() {
        addStudentAdminDashboard.setVisible(false);
        viewStudentAdminDashboard.setVisible(true);
    }
    @FXML
    private void showMainDashboard() {
        mainDashboard.setVisible(true);
        studentDashboard.setVisible(false);
        teacherDashboard.setVisible(false);
        createAccountDashboard.setVisible(false);
    }

    @FXML
    private void showStudentDashboard() {
        mainDashboard.setVisible(false);
        studentDashboard.setVisible(true);
        teacherDashboard.setVisible(false);
        createAccountDashboard.setVisible(false);
    }

    @FXML
    private void showTeacherDashboard() {
        mainDashboard.setVisible(false);
        studentDashboard.setVisible(false);
        teacherDashboard.setVisible(true);
        createAccountDashboard.setVisible(false);
    }

    @FXML
    private void showCreateAccountDashboard() {
        mainDashboard.setVisible(false);
        studentDashboard.setVisible(false);
        teacherDashboard.setVisible(false);
        createAccountDashboard.setVisible(true);
    }

    @FXML
    private void handleLogout() {
        mainApp.showLoginScreen();
    }

    private User loggedInUser;

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
//        welcomeLabel.setText("Welcome, " + user.getUsername() + "!");
//        roleLabel.setText("Role: " + user.getRole());
    }


//    ------------------------------------------------------------------------------------------
@FXML
public void initialize() {
    userList = FXCollections.observableArrayList();
    snColumn.setCellValueFactory(new PropertyValueFactory<>("sn"));
    usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
    passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
    roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
    userTable.setItems(userList);
    loadUsersFromFile();

    userTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
            usernameField.setText(newSelection.getUsername());
            passwordField.setText(newSelection.getPassword());
            confirmPasswordField.setText(newSelection.getPassword());
            roleField.setText(newSelection.getRole());
        }
    });
}

    @FXML
    public void handleCreateButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String role = roleField.getText();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || role.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled!");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Passwords do not match!");
            return;
        }

        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                showAlert(Alert.AlertType.ERROR, "Error", "Username must be unique!");
                return;
            }
        }

        if (confirmAction("Create User", "Do you want to create this user?")) {
            int sn = counter.incrementAndGet();
            User user = new User(sn, username, password, role);
            userList.add(user);
            saveUsersToFile();

            // Clear the text fields
            usernameField.clear();
            passwordField.clear();
            confirmPasswordField.clear();
            roleField.clear();
        }
    }

    @FXML
    public void handleUpdateButtonAction() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            if (confirmAction("Update User", "Do you want to update this user?")) {
                String newUsername = usernameField.getText();
                String newPassword = passwordField.getText();
                String newConfirmPassword = confirmPasswordField.getText();
                String newRole = roleField.getText();

                if (newUsername.isEmpty() || newPassword.isEmpty() || newConfirmPassword.isEmpty() || newRole.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled!");
                    return;
                }

                if (!newPassword.equals(newConfirmPassword)) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Passwords do not match!");
                    return;
                }

                for (User user : userList) {
                    if (user.getUsername().equals(newUsername) && user != selectedUser) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Username must be unique!");
                        return;
                    }
                }

                selectedUser.setUsername(newUsername);
                selectedUser.setPassword(newPassword);
                selectedUser.setRole(newRole);

                userTable.refresh();
                saveUsersToFile();

                usernameField.clear();
                passwordField.clear();
                confirmPasswordField.clear();
                roleField.clear();
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Select a user to update!");
        }
    }

    @FXML
    public void handleReadButtonAction() {
        loadUsersFromFile();
        showAlert(Alert.AlertType.INFORMATION, "Read Users", "All users have been reloaded from the file.");
    }

    @FXML
    public void handleDeleteButtonAction() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            if (confirmAction("Delete User", "Do you want to delete this user?")) {
                userList.remove(selectedUser);
                saveUsersToFile();
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Select a user to delete!");
        }
    }

    private void loadUsersFromFile() {
        userList.clear();
        counter.set(0);  // Reset the counter to ensure unique IDs
        try (BufferedReader reader = new BufferedReader(new FileReader("StudentManagementData/loginData/credentials.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int sn = Integer.parseInt(parts[0]);
                    String username = parts[1];
                    String password = parts[2];
                    String role = parts[3];
                    User user = new User(sn, username, password, role);
                    userList.add(user);
                    if (sn > counter.get()) {
                        counter.set(sn);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveUsersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("StudentManagementData/loginData/credentials.txt"))) {
            for (User user : userList) {
                writer.write(user.toCSVFormat());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean confirmAction(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
        return result == ButtonType.OK;
    }

}
