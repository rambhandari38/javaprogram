package com.project.studentmanagement.controllers;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import com.project.studentmanagement.Main;
import com.project.studentmanagement.model.Student;
import com.project.studentmanagement.model.User;
import com.project.studentmanagement.model.TeacherStaff;
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
    }
//    -------------------------------------------------------------------------------------

    @FXML
    public void initialize() {
        // User Table Initialization
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

        // Teacher Table Initialization
        teacherStaffList = FXCollections.observableArrayList();
        teacherIDColumn.setCellValueFactory(new PropertyValueFactory<>("teacherID"));
        firstNameColumn1.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn1.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailColumn1.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        subjectTaughtColumn.setCellValueFactory(new PropertyValueFactory<>("subjectTaught"));
        experienceColumn.setCellValueFactory(new PropertyValueFactory<>("experience"));
        userTable1.setItems(teacherStaffList);
        loadFromCSV();

        userTable1.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                TeacherStaff selectedTeacherStaff = (TeacherStaff) newSelection;
                firstNameField1.setText(selectedTeacherStaff.getFirstName());
                lastNameField1.setText(selectedTeacherStaff.getLastName());
                emailField1.setText(selectedTeacherStaff.getEmail());
                phoneField.setText(selectedTeacherStaff.getPhone());
                departmentField.setText(selectedTeacherStaff.getDepartment());
                subjectTaughtField.setText(selectedTeacherStaff.getSubjectTaught());
                experienceField.setText(String.valueOf(selectedTeacherStaff.getExperience()));
            }
        });

//        _____________________________________________________
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

                // Check for unique username
                boolean usernameExists = userList.stream()
                        .anyMatch(user -> user.getUsername().equals(newUsername) && user != selectedUser);
                if (usernameExists) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Username must be unique!");
                    return;
                }

                // Update user details
                selectedUser.setUsername(newUsername);
                selectedUser.setPassword(newPassword);
                selectedUser.setRole(newRole);

                // Refresh the table and save changes
                userTable.refresh();
                saveUsersToFile();

                // Clear fields
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
    private TextField searchField5;

    @FXML
    private void searchUsers() {
        String searchText = searchField5.getText().toLowerCase();

        List<User> filteredList = userList.stream()
                .filter(user -> user.getUsername().toLowerCase().contains(searchText))
                .collect(Collectors.toList());

        userTable.setItems(FXCollections.observableArrayList(filteredList));
    }

    @FXML
    public void handleReadButtonAction() {
        usernameField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        roleField.clear();
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
//    ---------------------------------------------------------------------------------------------------------------------------------
        @FXML
        private Button addTeacherAndStaffButton;

        @FXML
        private TextField firstNameField1;

        @FXML
        private TextField lastNameField1;

        @FXML
        private TextField emailField1;

        @FXML
        private TextField phoneField;

        @FXML
        private TextField departmentField;

        @FXML
        private TextField subjectTaughtField;

        @FXML
        private TextField experienceField;

        @FXML
        private TextField searchField;

        @FXML
        private TableView<TeacherStaff> userTable1;

        @FXML
        private TableColumn<TeacherStaff, String> teacherIDColumn;

        @FXML
        private TableColumn<TeacherStaff, String> firstNameColumn1;

        @FXML
        private TableColumn<TeacherStaff, String> lastNameColumn1;

        @FXML
        private TableColumn<TeacherStaff, String> emailColumn1;

        @FXML
        private TableColumn<TeacherStaff, String> phoneColumn;

        @FXML
        private TableColumn<TeacherStaff, String> departmentColumn;

        @FXML
        private TableColumn<TeacherStaff, String> subjectTaughtColumn;

        @FXML
        private TableColumn<TeacherStaff, Integer> experienceColumn;

        @FXML
        private Button createButton1;

        @FXML
        private Button updateButton1;

        @FXML
        private Button readButton1;

        @FXML
        private Button deleteButton1;
        private ObservableList<TeacherStaff> teacherStaffList = FXCollections.observableArrayList();

    private void saveToCSV() {
        try (CSVWriter writer = new CSVWriter(new FileWriter("admindata/teacher.csv"))) {
            // Write header
            String[] header = {"TeacherID", "FirstName", "LastName", "Email", "Phone", "Department", "SubjectTaught", "Experience"};
            writer.writeNext(header);

            // Write data
            for (TeacherStaff teacher : teacherStaffList) {
                String[] record = {
                        teacher.getTeacherID(),
                        teacher.getFirstName(),
                        teacher.getLastName(),
                        teacher.getEmail(),
                        teacher.getPhone(),
                        teacher.getDepartment(),
                        teacher.getSubjectTaught(),
                        String.valueOf(teacher.getExperience())
                };
                writer.writeNext(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void readTeacherAndStaff() {
        clearFields();
    }


    private void loadFromCSV() {
        teacherStaffList.clear(); // Clear existing data to avoid duplicates
        try (CSVReader reader = new CSVReader(new FileReader("admindata/teacher.csv"))) {
            String[] header = reader.readNext(); // Read header (optional)
            String[] line;
            while ((line = reader.readNext()) != null) {
                // Create TeacherStaff object from CSV line
                TeacherStaff teacherStaff = new TeacherStaff(
                        line[0], // TeacherID
                        line[1], // FirstName
                        line[2], // LastName
                        line[3], // Email
                        line[4], // Phone
                        line[5], // Department
                        line[6], // SubjectTaught
                        Integer.parseInt(line[7]) // Experience
                );
                teacherStaffList.add(teacherStaff);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void createTeacherAndStaff() {
        // Logic to create a new teacher and staff
        String firstName = firstNameField1.getText();
        String lastName = lastNameField1.getText();
        String email = emailField1.getText();
        String phone = phoneField.getText();
        String department = departmentField.getText();
        String subjectTaught = subjectTaughtField.getText();

        // Validate and parse experience
        int experience;
        try {
            experience = Integer.parseInt(experienceField.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Creation Failed", "Experience should be a valid number.");
            return;
        }

        // Check for email uniqueness
        if (isEmailTaken(email)) {
            showAlert(Alert.AlertType.ERROR, "Creation Failed", "The email is already taken.");
            return;
        }

        // Generate a unique ID (or username if required)
        String id = generateTeacherID(); // Assuming this generates a unique ID

        // Create new TeacherStaff object
        TeacherStaff newTeacherStaff = new TeacherStaff(
                id, firstName, lastName, email, phone, department, subjectTaught, experience);

        // Add to the list and save
        teacherStaffList.add(newTeacherStaff);
        clearFields();
        saveToCSV();  // Save to CSV after adding a new record
        showAlert(Alert.AlertType.INFORMATION, "Creation Successful", "Teacher/Staff created successfully.");
    }

    private boolean isEmailTaken(String email) {
        for (TeacherStaff ts : teacherStaffList) {
            if (ts.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    @FXML
    private void updateTeacherAndStaff() {
        TeacherStaff selectedTeacherStaff = userTable1.getSelectionModel().getSelectedItem();
        if (selectedTeacherStaff != null) {
            if (confirmAction("Update Teacher/Staff", "Do you want to update this teacher/staff?")) {
                // Get new values from text fields
                String newFirstName = firstNameField1.getText();
                String newLastName = lastNameField1.getText();
                String newEmail = emailField1.getText();
                String newPhone = phoneField.getText();
                String newDepartment = departmentField.getText();
                String newSubjectTaught = subjectTaughtField.getText();
                int newExperience;

                // Validate fields
                if (newFirstName.isEmpty() || newLastName.isEmpty() || newEmail.isEmpty() || newPhone.isEmpty() ||
                        newDepartment.isEmpty() || newSubjectTaught.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled!");
                    return;
                }

                try {
                    newExperience = Integer.parseInt(experienceField.getText());
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Experience must be a valid number!");
                    return;
                }

                // Check for unique email
                boolean emailExists = teacherStaffList.stream()
                        .anyMatch(teacher -> teacher.getEmail().equals(newEmail) && teacher != selectedTeacherStaff);
                if (emailExists) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Email must be unique!");
                    return;
                }

                // Update teacher/staff details
                selectedTeacherStaff.setFirstName(newFirstName);
                selectedTeacherStaff.setLastName(newLastName);
                selectedTeacherStaff.setEmail(newEmail);
                selectedTeacherStaff.setPhone(newPhone);
                selectedTeacherStaff.setDepartment(newDepartment);
                selectedTeacherStaff.setSubjectTaught(newSubjectTaught);
                selectedTeacherStaff.setExperience(newExperience);

                // Refresh the table and save changes to CSV
                userTable1.refresh();
                saveToCSV();

                // Clear fields
                clearFields();
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Select a teacher/staff to update!");
        }
    }

    @FXML
    private void deleteTeacherAndStaff() {
        // Logic to delete a teacher and staff
        TeacherStaff selectedTeacherStaff = userTable1.getSelectionModel().getSelectedItem();
        if (selectedTeacherStaff != null) {
            teacherStaffList.remove(selectedTeacherStaff);
            clearFields();
            saveToCSV();  // Save to CSV after deleting a record
        }
    }


    @FXML
    private void searchTeacherAndStaff() {
        String searchQuery = searchField.getText().toLowerCase();
        ObservableList<TeacherStaff> filteredList = FXCollections.observableArrayList();

        if (searchQuery.isEmpty()) {
            userTable1.setItems(teacherStaffList); // Show all data if search is empty
            return;
        }

        for (TeacherStaff teacherStaff : teacherStaffList) {
            if (teacherStaff.getFirstName().toLowerCase().contains(searchQuery) ||
                    teacherStaff.getLastName().toLowerCase().contains(searchQuery) ||
                    teacherStaff.getEmail().toLowerCase().contains(searchQuery)) {
                    filteredList.add(teacherStaff);
            }
        }

        userTable1.setItems(filteredList);
    }

    private void clearFields() {
            firstNameField1.clear();
            lastNameField1.clear();
            emailField1.clear();
            phoneField.clear();
            departmentField.clear();
            subjectTaughtField.clear();
            experienceField.clear();
        }

        private String generateTeacherID() {
            // Generate a unique teacher ID (for simplicity, using the size of the list)
            return "T" + (teacherStaffList.size() + 1);
        }
//------------------------------------------------------------------------------
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