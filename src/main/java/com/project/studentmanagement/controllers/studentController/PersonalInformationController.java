package com.project.studentmanagement.controllers.studentController;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonalInformationController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField dobField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField genderField;

    private final String CSV_FILE_PATH = "studentData/personal_information.csv";
    private static final Logger LOGGER = Logger.getLogger(PersonalInformationController.class.getName());

    @FXML
    private void initialize() {
        // Load personal information from CSV
        loadPersonalInformation();
    }

    private void loadPersonalInformation() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            // Skip the header line
            reader.readLine();

            // Read the actual data line
            String line = reader.readLine();
            if (line != null) {
                List<String> data = List.of(line.split(","));
                if (data.size() == 7) {
                    firstNameField.setText(data.get(0));
                    lastNameField.setText(data.get(1));
                    emailField.setText(data.get(2));
                    addressField.setText(data.get(3));
                    dobField.setText(data.get(4));
                    phoneField.setText(data.get(5));
                    genderField.setText(data.get(6));
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading personal information from CSV", e);
        }
    }

    @FXML
    private void handleSave() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH))) {
            // Write the header
            writer.write("FirstName,LastName,Email,Address,DOB,Phone,Gender");
            writer.newLine();

            // Write the personal information
            writer.write(String.format("%s,%s,%s,%s,%s,%s,%s",
                    firstNameField.getText(),
                    lastNameField.getText(),
                    emailField.getText(),
                    addressField.getText(),
                    dobField.getText(),
                    phoneField.getText(),
                    genderField.getText()));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving personal information to CSV", e);
        }
    }
}
