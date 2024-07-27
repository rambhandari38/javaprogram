package com.project.studentmanagement;

import com.project.studentmanagement.controllers.*;
import com.project.studentmanagement.controllers.teacherController.TeacherDashboardController;
import com.project.studentmanagement.model.User;
import com.project.studentmanagement.controllers.bookController.OverViewController;
import com.project.studentmanagement.controllers.studentController.StudentDashboard;
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

    private static final String USER_DATA_FILE = "StudentManagementData/loginData/users.txt";
    private static final String CREDENTIALS_FILE = "StudentManagementData/loginData/credentials.txt";
    private Stage primaryStage;
    private List<User> users = new ArrayList<>();
    private Map<String, String> credentials = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.initStyle(StageStyle.UNDECORATED);

        loadCredentials();
        loadUserData();
        setupDefaultAdmin(); // Ensure default admin is added if not already present
        showLoginScreen();
    }

    private void loadUserData() {
        File file = new File(USER_DATA_FILE);
        if (!file.exists()) {
            // If the file does not exist, create a default admin user and save it
            users.add(new User(1, "admin", "admin", "Admin")); // Set SN=1 for default admin
            saveUserData();
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 4) {
                        int sn = Integer.parseInt(parts[0]);
                        String username = parts[1];
                        String password = parts[2]; // Changed from hashedPassword to password
                        String role = parts[3];
                        users.add(new User(sn, username, password, role));
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
                if (parts.length == 4) { // Ensure four parts
                    int sn = Integer.parseInt(parts[0]);
                    String username = parts[1];
                    String password = parts[2]; // Changed from hashedPassword to password
                    String role = parts[3];
                    credentials.put(username, password + "," + role); // Handle role for login
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
            users.add(new User(1, "admin", "admin", "Admin")); // Set SN=1
            saveUserData();
        }
    }

    private void saveUserData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE))) {
            for (User user : users) {
                writer.write(user.toCSVFormat()); // Convert to CSV format including SN
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/LoginForm.fxml"));
            Parent root = loader.load();

            LoginController controller = loader.getController();
            controller.setMainApp(this);
            controller.setStage(primaryStage);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Login");
            primaryStage.centerOnScreen();
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
                    adminController.setStage(primaryStage);
                    break;
                case "Teacher":
                    TeacherDashboardController teacherController = loader.getController();
                    teacherController.setMainApp(this);
                    teacherController.setStage(primaryStage);
                break;
                case "Student":
                    StudentDashboard studentDashboard = loader.getController();
                    studentDashboard.setMainApp(this);
                    break;
                case "Admission Officer":
                    AdmissionOfficerDashboardController admissionOfficerController = loader.getController();
                    admissionOfficerController.setMainApp(this);
                    admissionOfficerController.setStage(primaryStage);
                    break;
                case "Librarian":
                    OverViewController overViewController  = loader.getController();
                    overViewController.setMainApp(this);
                    break;
                // Handle other roles similarly if needed
                default:
                    break;
            }

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle(user.getRole() + " Dashboard");
            primaryStage.centerOnScreen(); // Center the window on the screen
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getDashboardFXML(String role) {
        switch (role) {
            case "Admin":
                return "fxml/AdminDashboard.fxml";
            case "Teacher":
                return "fxml/teacherfxml/TeacherDashboard.fxml";
            case "Student":
                return "fxml/studentfxml/StudentDashboard.fxml";
            case "Admission Officer":
                return "fxml/AdmissionOfficerDashboard.fxml";
            case "Librarian":
                return "fxml/bookFxml/Overview.fxml";
            default:
                return "fxml/LoginForm.fxml"; // Default dashboard for unknown roles
        }
    }

    public User validateLogin(String username, String password) {
        // Check in users (user.txt)
        for (User user : users) {
            if (user.getUsername().equals(username) && user.verifyPassword(password)) {
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
