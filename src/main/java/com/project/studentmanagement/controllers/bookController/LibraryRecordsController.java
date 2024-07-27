package com.project.studentmanagement.controllers.bookController;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.project.studentmanagement.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class LibraryRecordsController {

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleCloseButtonAction(ActionEvent event) {
        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Close the stage
        stage.close();
    }

    private Main mainApp;
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    @FXML
    private BorderPane contentPane;

    @FXML
    private TextField bookTitle, borrowerName, returnBookName, returnName;
    @FXML
    private DatePicker borrowedDate, dueDate, returnedDate;


    @FXML
    private TableView<List<String>> borrowTable;
    @FXML
    private TableColumn<List<String>, String> borrowedBookTC;
    @FXML
    private TableColumn<List<String>, String> borrowerNameTC;
    @FXML
    private TableColumn<List<String>, String> borrowedDateTC;
    @FXML
    private TableColumn<List<String>, String> dueDateTC;
    @FXML
    private TableColumn<List<String>, String> returnDateTC;
    @FXML
    private TableColumn<List<String>, String> actionTC;

    private final String pathToBorrowCSV = "StudentManagementData/libraryData/borrowBook.csv";
    private final String pathToBookCSV = "StudentManagementData/libraryData/bookData.csv";

    public void initialize(){
        borrowedDate.setValue(LocalDate.now());
        dueDate.setValue(LocalDate.now().plusMonths(3));
        returnedDate.setValue(LocalDate.now());


        ObservableList<List<String>> data = FXCollections.observableArrayList();
        try {
            CSVReader csvReader = new CSVReader(new FileReader(pathToBorrowCSV));
            List<String[]> allData = csvReader.readAll();
            for (String[] row : allData) {
                data.add(FXCollections.observableArrayList(row));
            }
            csvReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        borrowedBookTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0)));
        borrowerNameTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1)));
        borrowedDateTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2)));
        dueDateTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(3)));
        returnDateTC.setCellValueFactory(cellData -> {
                    if (cellData.getValue().size() > 4) {
                        return new SimpleStringProperty(cellData.getValue().get(4));
                    } else {
                        return new SimpleStringProperty("");
                    }
                });
//        returnDateTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(3)));

        addActionColumn();
        borrowTable.setItems(data);
    }

    private void loadFXML(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlFile));
            Node node = loader.load();
            contentPane.getChildren().setAll(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void bookMngmtAction(ActionEvent actionEvent) {
        loadFXML("fxml/bookFxml/BookManagement.fxml");
    }

    public void libraryReviewAction(ActionEvent actionEvent) {
        loadFXML("fxml/bookFxml/LibraryManagement.fxml");
    }

    public void overviewAction(ActionEvent actionEvent) {
        loadFXML("fxml/bookFxml/Overview.fxml");
    }

    public void borrowBookAction(ActionEvent actionEvent) {
        if (checkBorrowBook()){
            if (isBookInDetails(bookTitle.getText())){
                try {
                    FileWriter fileWriter = new FileWriter(pathToBorrowCSV, true);
                    CSVWriter csvWriter = new CSVWriter(fileWriter);
                    String[] data = {bookTitle.getText(), borrowerName.getText(), String.valueOf(borrowedDate.getValue()), String.valueOf(dueDate.getValue())};
                    csvWriter.writeNext(data);
                    csvWriter.close();
                    initialize();
                }
                catch (Exception e){
                    e.getMessage();
                }
            }
            else {
                showAlert("Book Not Found","No such book found in the library");
            }

        }
        else
            showAlert();
    }

    public boolean isBookInDetails(String bookName) {
        try {
            CSVReader reader = new CSVReader(new FileReader(pathToBookCSV));
            List<String[]> allData = reader.readAll();
            for (String[] row : allData) {
                String storedBookName = row[0].replace("\"", "").trim();
                if (storedBookName.equalsIgnoreCase(bookName.trim())) {
                    reader.close();
                    return true; // Book found
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void returnBookAction(ActionEvent actionEvent) {
        if (checkReturnBook()){
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Return Book Confirmation");
            dialog.setHeaderText("Confirm the book title to return:");
            dialog.setContentText("Book Title:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(this::returnBook);
        }
        else
            showAlert();
    }

    public void returnBook(String bookTitle) {
        try {
            File inputFile = new File(pathToBorrowCSV);
            File tempFile = new File("temp.csv");

            CSVReader reader = new CSVReader(new FileReader(inputFile));
            CSVWriter writer = new CSVWriter(new FileWriter(tempFile));

            List<String[]> allData = reader.readAll();
            boolean bookFound = false;
            for (String[] row : allData) {
                if (row[0].equals(bookTitle) && row.length == 4) {

                    String[] newRow = {row[0], row[1], row[2], row[3], String.valueOf(returnedDate.getValue())};
                    writer.writeNext(newRow);
                    bookFound = true;
                } else {
                    writer.writeNext(row);
                }
            }

            writer.close();
            reader.close();

            if (bookFound) {
                inputFile.delete();
                tempFile.renameTo(inputFile);
                initialize();
            } else {
                showAlert("Book not found", "The book with the given title was not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean checkReturnBook() {
        return (isReturnBookCorrect(returnBookName.getText()) &&
                isReturnNameCorrect(returnName.getText()));
    }

    private boolean isReturnBookCorrect(String returnBook) {
        return !returnBook.isEmpty();
    }

    private boolean isReturnNameCorrect(String returnName) {
        return !returnName.isEmpty();
    }

    private boolean checkBorrowBook() {
        return (isBookNameCorrect(bookTitle.getText()) &&
                isborrowerNameCorrect(borrowerName.getText())
        );

    }

    private boolean isBookNameCorrect(String bookName) {
        return !bookName.isEmpty();
    }

    private boolean isborrowerNameCorrect(String author) {
        return !author.isEmpty();
    }

    public void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText("Please fill in all the required details correctly.");

        alert.getButtonTypes().setAll(ButtonType.OK);

        alert.showAndWait();
    }

    private void addActionColumn() {
        actionTC.setCellFactory(new Callback<>() {
            @Override
            public TableCell<List<String>, String> call(final TableColumn<List<String>, String> param) {
                final TableCell<List<String>, String> cell = new TableCell<>() {

                    final Button editButton = new Button("Edit");
                    final Button deleteButton = new Button("Del");
                    final HBox hbox = new HBox(deleteButton, editButton);

                    {
                        deleteButton.setOnAction(event -> {
                            List<String> book = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this book?", ButtonType.YES, ButtonType.NO);
                            alert.showAndWait();

                            if (alert.getResult() == ButtonType.YES) {
                                deleteBookFromCSV(book);
                                initialize();
                            }
                        });
                        editButton.setOnAction(event -> {
                            List<String> book = getTableView().getItems().get(getIndex());
                            editBook(book);
                        });
                    }

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            setGraphic(hbox);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });
    }

    private void editBook(List<String> book) {
        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle("Edit Book Details");
        dialog.setHeaderText("Edit the details of the book");

        ButtonType editButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(editButtonType, ButtonType.CANCEL);

        TextField borrowedBookField = new TextField(book.get(0));
        TextField borrowedNameField = new TextField(book.get(1));

        DatePicker borrowedDatePicker = new DatePicker();
        borrowedDatePicker.setValue(LocalDate.parse(book.get(2)));

        DatePicker dueDatePicker = new DatePicker();
        dueDatePicker.setValue(LocalDate.parse(book.get(3)));

        DatePicker returnDatePicker = new DatePicker();
        if (book.size() > 4) {
            returnDatePicker.setValue(LocalDate.parse(book.get(4)));
        } else {
            returnDatePicker.setValue(null);
        }

        dialog.getDialogPane().setContent(new VBox(8, new Label("Book Title:"), borrowedBookField,
                new Label("Borrower Name:"), borrowedNameField,
                new Label("Borrow Date:"), borrowedDatePicker,
                new Label("Due Date:"), dueDatePicker,
                new Label("Return Date:"), returnDatePicker));

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == editButtonType) {
                return List.of(borrowedBookField.getText(), borrowedNameField.getText(), borrowedDatePicker.getValue().toString(), dueDatePicker.getValue().toString(), returnDatePicker.getValue() != null ? returnDatePicker.getValue().toString() : "");
            }
            return null;
        });

        Optional<List<String>> result = dialog.showAndWait();
        result.ifPresent(newBookDetails -> {
            updateBookInCSV(book, newBookDetails);
            initialize();
        });
    }


    private void updateBookInCSV(List<String> oldBook, List<String> newBook) {
        try {
            File inputFile = new File(pathToBorrowCSV);
            File tempFile = new File("temp.csv");

            CSVReader reader = new CSVReader(new FileReader(inputFile));
            CSVWriter writer = new CSVWriter(new FileWriter(tempFile));

            List<String[]> allData = reader.readAll();
            for (String[] row : allData) {
                if (row[0].equals(oldBook.get(0)) && row[1].equals(oldBook.get(1)) && row[2].equals(oldBook.get(2)) && row[3].equals(oldBook.get(3))) {
                    writer.writeNext(newBook.toArray(new String[0]));
                } else {
                    writer.writeNext(row);
                }
            }
            writer.close();
            reader.close();

            inputFile.delete();
            tempFile.renameTo(inputFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void deleteBookFromCSV(List<String> book) {
        try {
            File inputFile = new File(pathToBorrowCSV);
            File tempFile = new File("temp.csv");

            CSVReader reader = new CSVReader(new FileReader(inputFile));
            CSVWriter writer = new CSVWriter(new FileWriter(tempFile));

            List<String[]> allData = reader.readAll();
            for (String[] row : allData) {
                if (!row[0].equals(book.get(0)) || !row[1].equals(book.get(1)) || !row[2].equals(book.get(2)) || !row[3].equals(book.get(3))) {
                    writer.writeNext(row);
                }
            }
            writer.close();
            reader.close();

            inputFile.delete();
            tempFile.renameTo(inputFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
