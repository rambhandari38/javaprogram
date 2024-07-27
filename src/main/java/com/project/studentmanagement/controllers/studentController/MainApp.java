package com.project.studentmanagement.controllers.studentController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentDashboard.fxml"));
            BorderPane root = loader.load();

            // Create and set up the scene
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Student Dashboard");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to load the FXML file. Please check the file path and ensure it is in the resources directory.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
