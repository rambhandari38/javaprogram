package com.project.studentmanagement.controllers;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import com.project.studentmanagement.Main;
import com.project.studentmanagement.model.Student;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
public class AdmissionOfficerDashboardController {
    @FXML
    private AnchorPane mainDashboard;
    @FXML
    private AnchorPane studentDashboard;
    @FXML
    private AnchorPane addStudentAdminDashboard;
    @FXML
    private AnchorPane viewStudentAdminDashboard;

    private List<User> users = new ArrayList<>();
    private static final String CSV_FILE = "admindata/users.csv";
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
    private void showMainDashboard() {
        mainDashboard.setVisible(true);
        studentDashboard.setVisible(false);
    }

    @FXML
    private void showStudentDashboard() {
        mainDashboard.setVisible(false);
        studentDashboard.setVisible(true);
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
        studentIDColumn2.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        firstNameColumn2.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn2.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        facultyColumn2.setCellValueFactory(new PropertyValueFactory<>("faculty"));
        emailColumn2.setCellValueFactory(new PropertyValueFactory<>("email"));
        genderColumn2.setCellValueFactory(new PropertyValueFactory<>("gender"));
        phoneNumberColumn2.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        addressColumn2.setCellValueFactory(new PropertyValueFactory<>("address"));
        dobColumn2.setCellValueFactory(new PropertyValueFactory<>("dob"));
        enrollDateColumn2.setCellValueFactory(new PropertyValueFactory<>("enrollDate"));
        studentTableView2.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> onStudentSelected());

        // Bind student list to TableView
        studentTableView2.setItems(studentList);
//        loadStudentsFromCSV();
        loadStudentsFromFile();

        // Additional initialization if needed
    }

//    --------------------------------------------------------------------------------------------------------------------------

    @FXML private AnchorPane addStudentAdminDashboard2;
    @FXML private TextField firstNameField2;
    @FXML private TextField lastNameField2;
    @FXML private TextField facultyField2;
    @FXML private TextField emailField2;
    @FXML private TextField genderField2;
    @FXML private TextField phoneNumberField2;
    @FXML private TextField addressField2;
    @FXML private TextField statusField2;
    @FXML private DatePicker dobPicker2;
    @FXML private DatePicker enrollDatePicker2;
    @FXML private Button createStudent2;
    @FXML private Button readStudent2;
    @FXML private Button updateStudent2;
    @FXML private Button deleteStudent2;
    @FXML private TextField searchField2;
    @FXML private Button searchStudent2;
    @FXML private TableView<Student> studentTableView2;
    @FXML private TableColumn<Student, Integer> studentIDColumn2;
    @FXML private TableColumn<Student, String> firstNameColumn2;
    @FXML private TableColumn<Student, String> lastNameColumn2;
    @FXML private TableColumn<Student, String> facultyColumn2;
    @FXML private TableColumn<Student, String> emailColumn2;
    @FXML private TableColumn<Student, String> genderColumn2;
    @FXML private TableColumn<Student, String> phoneNumberColumn2;
    @FXML private TableColumn<Student, String> addressColumn2;
    @FXML private TableColumn<Student, String> dobColumn2;
    @FXML private TableColumn<Student, String> enrollDateColumn2;

    private ObservableList<Student> studentList = FXCollections.observableArrayList();

    @FXML
    private void saveStudentsToCSV() {
        try (CSVWriter writer = new CSVWriter(new FileWriter("admindata/students.csv"))) {
            // Write header
            writer.writeNext(new String[]{"StudentID", "FirstName", "LastName", "Faculty", "Email", "Gender", "PhoneNumber", "Address", "DOB", "EnrollDate"});

            // Write data
            for (Student student : studentList) {
                writer.writeNext(new String[]{
                        String.valueOf(student.getStudentID()),
                        student.getFirstName(),
                        student.getLastName(),
                        student.getFaculty(),
                        student.getEmail(),
                        student.getGender(),
                        student.getPhoneNumber(),
                        student.getAddress(),
                        student.getDob().format(DateTimeFormatter.ISO_LOCAL_DATE),
                        student.getEnrollDate().format(DateTimeFormatter.ISO_LOCAL_DATE)
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadStudentsFromFile() {
        File file = new File("admindata/students.csv");

        if (!file.exists()) {
            return;
        }

        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            List<String[]> records = reader.readAll();
            records.remove(0); // Remove header row

            for (String[] record : records) {
                int studentID = Integer.parseInt(record[0]);
                String firstName = record[1];
                String lastName = record[2];
                String faculty = record[3];
                String email = record[4];
                String gender = record[5];
                String phoneNumber = record[6];
                String address = record[7];
                LocalDate dob = LocalDate.parse(record[8]);
                LocalDate enrollDate = LocalDate.parse(record[9]);

                Student student = new Student(studentID, firstName, lastName, faculty, email, gender, phoneNumber, address, dob, enrollDate);
                studentList.add(student);
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while reading the file", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void createStudent2() {
        String firstName = firstNameField2.getText();
        String lastName = lastNameField2.getText();
        String faculty = facultyField2.getText();
        String email = emailField2.getText();
        String gender = genderField2.getText();
        String phoneNumber = phoneNumberField2.getText();
        String address = addressField2.getText();
        LocalDate dob = dobPicker2.getValue();
        LocalDate enrollDate = enrollDatePicker2.getValue();

        // Ensure all fields are filled
        if (firstName.isEmpty() || lastName.isEmpty() || faculty.isEmpty() || email.isEmpty() ||
                gender.isEmpty() || phoneNumber.isEmpty() || address.isEmpty() || dob == null || enrollDate == null) {
            showAlert("Error", "Please fill all fields", Alert.AlertType.ERROR);
            return;
        }

        // Check for unique email
        if (isEmailDuplicate(email)) {
            showAlert("Error", "Email already exists", Alert.AlertType.ERROR);
            return;
        }

        // Check for unique phone number
        if (isPhoneNumberDuplicate(phoneNumber)) {
            showAlert("Error", "Phone number already exists", Alert.AlertType.ERROR);
            return;
        }

        // Generate unique student ID
        int studentID = generateUniqueStudentID();

        // Create a new student
        Student student = new Student(studentID, firstName, lastName, faculty, email, gender, phoneNumber, address, dob, enrollDate);
        studentList.add(student);

        // Write new student data to the CSV file
        saveStudentsToCSV();

        // Clear fields
        clearFields3();
    }

    private boolean isEmailDuplicate(String email) {
        // Check if the email exists in the studentList
        return studentList.stream()
                .anyMatch(student -> student.getEmail().equalsIgnoreCase(email));
    }

    private boolean isPhoneNumberDuplicate(String phoneNumber) {
        // Check if the phone number exists in the studentList
        return studentList.stream()
                .anyMatch(student -> student.getPhoneNumber().equals(phoneNumber));
    }

    private int generateUniqueStudentID() {
        // Check the current highest studentID in the list
        int maxID = studentList.stream()
                .mapToInt(Student::getStudentID)
                .max()
                .orElse(0);
        return maxID + 1;
    }




    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields3() {
        firstNameField2.clear();
        lastNameField2.clear();
        facultyField2.clear();
        emailField2.clear();
        genderField2.clear();
        phoneNumberField2.clear();
        addressField2.clear();
        dobPicker2.setValue(null);
        enrollDatePicker2.setValue(null);
    }

    @FXML
    private void readStudent2(){
        clearFields3();
    }

    @FXML
    private void onStudentSelected() {
        Student selectedStudent = studentTableView2.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            // Populate text fields with selected student's data
            firstNameField2.setText(selectedStudent.getFirstName());
            lastNameField2.setText(selectedStudent.getLastName());
            facultyField2.setText(selectedStudent.getFaculty());
            emailField2.setText(selectedStudent.getEmail());
            genderField2.setText(selectedStudent.getGender());
            phoneNumberField2.setText(selectedStudent.getPhoneNumber());
            addressField2.setText(selectedStudent.getAddress());
            dobPicker2.setValue(selectedStudent.getDob());
            enrollDatePicker2.setValue(selectedStudent.getEnrollDate());
        }
    }

    @FXML
    private void updateStudent2() {
        Student selectedStudent = studentTableView2.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            selectedStudent.setFirstName(firstNameField2.getText());
            selectedStudent.setLastName(lastNameField2.getText());
            selectedStudent.setFaculty(facultyField2.getText());
            selectedStudent.setEmail(emailField2.getText());
            selectedStudent.setGender(genderField2.getText());
            selectedStudent.setPhoneNumber(phoneNumberField2.getText());
            selectedStudent.setAddress(addressField2.getText());
            selectedStudent.setDob(dobPicker2.getValue());
            selectedStudent.setEnrollDate(enrollDatePicker2.getValue());

            // Refresh the table to show the updated data
            studentTableView2.refresh();

            // Save updated student list to CSV
            saveStudentsToCSV();

            // Clear fields after updating
            clearFields3();
        } else {
            showAlert("Error", "No student selected for update", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void deleteStudent2() {
        Student selectedStudent = studentTableView2.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            studentList.remove(selectedStudent);
            saveStudentsToCSV(); // Update the CSV file after deletion
        } else {
            showAlert("Error", "No student selected", Alert.AlertType.ERROR);
        }
    }


    @FXML
    private void searchStudent2() {
        String searchText = searchField2.getText().toLowerCase();

        List<Student> filteredList = studentList.stream()
                .filter(student -> student.getFirstName().toLowerCase().contains(searchText) ||
                        student.getLastName().toLowerCase().contains(searchText) ||
                        student.getFaculty().toLowerCase().contains(searchText) ||
                        student.getEmail().toLowerCase().contains(searchText) ||
                        student.getGender().toLowerCase().contains(searchText) ||
                        student.getPhoneNumber().toLowerCase().contains(searchText) ||
                        student.getAddress().toLowerCase().contains(searchText) ||
                        String.valueOf(student.getStudentID()).contains(searchText))
                .collect(Collectors.toList());

        studentTableView2.setItems(FXCollections.observableArrayList(filteredList));
    }
}
