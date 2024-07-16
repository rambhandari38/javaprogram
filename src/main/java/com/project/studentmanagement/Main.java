package com.project.studentmanagement;

import com.project.studentmanagement.controllers.*;
import com.project.studentmanagement.model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends Application {

    private static final String USER_DATA_FILE = "users.txt";
    private static final String CREDENTIALS_FILE = "credentials.txt";
    private Stage primaryStage;
    private List<User> users = new ArrayList<>();
    private Map<String, String> credentials = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        loadUserData();
        loadCredentials();
        setupDefaultAdmin(); // Ensure default admin is added if not already present
        showLoginScreen();
    }

    public List<User> getUsers() {
        return users;
    }

    private void loadUserData() {
        File file = new File(USER_DATA_FILE);
        if (!file.exists()) {
            // If the file does not exist, create a default admin user and save it
            users.add(new User("admin", "admin", "Admin"));
            saveUserData();
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        users.add(new User(parts[0], parts[1], parts[2]));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadCredentials() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CREDENTIALS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    credentials.put(parts[0], parts[1] + "," + parts[2]); // Added for role handling
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupDefaultAdmin() {
        boolean adminExists = false;
        for (User user : users) {
            if (user.getUsername().equals("admin")) {
                adminExists = true;
                break;
            }
        }
        if (!adminExists) {
            users.add(new User("admin", "admin", "Admin"));
            saveUserData();
        }
    }

    private void saveUserData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE))) {
            for (User user : users) {
                writer.write(user.getUsername() + "," + user.getPassword() + "," + user.getRole());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginForm1.fxml"));
            Parent root = loader.load();

            LoginController controller = loader.getController();
            controller.setMainApp(this);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Login");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDashboard(User user) {
        try {
            FXMLLoader loader = new FXMLLoader();
            String dashboardFXML = getDashboardFXML(user.getRole()); // Determine which dashboard to load based on user role
            loader.setLocation(getClass().getResource(dashboardFXML));
            Parent root = loader.load();

            switch (user.getRole()) {
                case "Admin":
                    AdminDashboardController adminController = loader.getController();
                    adminController.setMainApp(this);
                    adminController.setLoggedInUser(user);
                    break;
                case "Teacher":
                    TeacherDashboardController  teacherController = loader.getController();
                    teacherController.setMainApp(this);
                    teacherController.setLoggedInUser(user);
                    break;
                case "Student":
                    StudentDashboardController studentController = loader.getController();
                    studentController.setMainApp(this);
                    studentController.setLoggedInUser(user);
                    break;
                    case "Admission Officer":
                    AdmissionOfficerDashboardController admissionOfficerController = loader.getController();
                    admissionOfficerController.setMainApp(this);
                    admissionOfficerController.setLoggedInUser(user);
                    break;
                case "Librarian":
                    LibrarianDashboardController librarianController = loader.getController();
                    librarianController.setMainApp(this);
                    librarianController.setLoggedInUser(user);
                    break;
                // Handle other roles similarly if needed
                default:
                    break;
            }

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle(user.getRole() + " Dashboard");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getDashboardFXML(String role) {
        switch (role) {
            case "Admin":
                return "AdminDashboard.fxml";
            case "Teacher":
                return "TeacherDashboard.fxml";
           case "Student":
                return "StudentDashboard.fxml";
            case "Admission Officer":
                return "AdmissionOfficerDashboard.fxml";
            case "Librarian":
                return "LibrarianDashboard.fxml";
            // Add cases for other roles as needed
            default:
                return "LoginForm.fxml"; // Default dashboard for unknown roles
        }
    }

    public User validateLogin(String username, String password) {
        // Check in admin users (user.txt)
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }

        // Check in additional users (credentials.txt)
        String storedInfo = credentials.get(username);
        if (storedInfo != null) {
            String[] parts = storedInfo.split(",");
            String storedPassword = parts[0];
            String role = parts[1];
            if (storedPassword.equals(password)) {
                return new User(username, storedPassword, role);
            }
        }

        return null;
    }

    public void addUser(User user) {
        users.add(user);
        saveUserData();
    }

    public void addUserToFile(User user) {
        credentials.put(user.getUsername(), user.getPassword() + "," + user.getRole()); // Added for role handling
        saveCredentials();
    }

    private void saveCredentials() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CREDENTIALS_FILE))) {
            for (Map.Entry<String, String> entry : credentials.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
