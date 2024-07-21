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
import javafx.util.Callback;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class BookManagementController {
    @FXML
    private BorderPane contentPane;

    private Main mainApp;
//    private User loggedInUser;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private Label bookAddedPrompt;

    @FXML
    private TextField bookTitle, author, isbn, date;

    @FXML
    private DatePicker bookAddedDate;

    @FXML
    private TableView<List<String>> bookTable;
    @FXML
    private TableColumn<List<String>, String> bookTitleColumn;
    @FXML
    private TableColumn<List<String>, String> authorColumn;
    @FXML
    private TableColumn<List<String>, String> isbnColumn;
    @FXML
    private TableColumn<List<String>, String> dateColumn;
    @FXML
    private TableColumn<List<String>, String> actionColumn;

    String pathToCSV = "StudentManagementData/libraryData/bookData.csv";

    public void initialize() {
        bookAddedDate.setValue(LocalDate.now());
        ObservableList<List<String>> data = FXCollections.observableArrayList();

        try {
            CSVReader csvReader = new CSVReader(new FileReader(pathToCSV));
            List<String[]> allData = csvReader.readAll();
            for (String[] row : allData) {
                data.add(FXCollections.observableArrayList(row));
            }
            csvReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        bookTitleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0)));
        authorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1)));
        isbnColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2)));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(3)));

        addActionColumn();

        bookTable.setItems(data);
    }

//    private void loadFXML(String fxmlFile) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
//            Node node = loader.load();
//            contentPane.getChildren().setAll(node);
//
//        } catch (IOException e) {
//            bookAddedPrompt.setText("Error");
//        }
//    }

    private void loadFXML(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(com.project.studentmanagement.Main.class.getResource(fxmlFile));
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

    public void addBookAction(ActionEvent actionEvent) {
        if (checkBookDetails()){
            try {
                FileWriter fileWriter = new FileWriter(pathToCSV, true);
                CSVWriter csvWriter = new CSVWriter(fileWriter);
                String[] data = {bookTitle.getText(), author.getText(), isbn.getText(), String.valueOf(bookAddedDate.getValue())};
                csvWriter.writeNext(data);
                csvWriter.close();
                initialize();
            }
            catch (Exception e){
                e.getMessage();
            }

            bookAddedPrompt.setText("Book Added Successfully");
            bookAddedPrompt.setStyle("-fx-text-fill: green;");
            bookAddedPrompt.setVisible(true);
        }
        else {
            bookAddedPrompt.setText("INVALID INFORMATION");
            bookAddedPrompt.setStyle("-fx-text-fill: red;");
            bookAddedPrompt.setVisible(true);
        }
    }

    private boolean checkBookDetails() {
            return (isBookNameCorrect(bookTitle.getText()) &&
                    isAuthorNameCorrect(author.getText()) &&
                    isIsbnCorrect()
            );

    }

    private boolean isBookNameCorrect(String bookName) {
        return !bookName.isEmpty();
    }

    private boolean isAuthorNameCorrect(String author) {
        return !author.isEmpty();
    }

    private boolean isIsbnCorrect() {
        String isbnNumber = isbn.getText().trim();
        return (isbn10(isbnNumber) || isbn13(isbnNumber));
    }

    private boolean isbn13(String isbnNumber) {
        isbnNumber = isbnNumber.replaceAll("[-\\s]", "");
        return (isbnNumber.length() == 13);
    }

    private boolean isbn10(String isbnNumber) {
        return (isbnNumber.length() == 10);
    }

    private void addActionColumn() {
        actionColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<List<String>, String> call(final TableColumn<List<String>, String> param) {
                final TableCell<List<String>, String> cell = new TableCell<>() {

                    final Button editButton = new Button("Edit");
                    final Button deleteButton = new Button("Delete");
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

        TextField bookTitleField = new TextField(book.get(0));
        TextField authorField = new TextField(book.get(1));
        TextField isbnField = new TextField(book.get(2));
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.parse(book.get(3)));

        dialog.getDialogPane().setContent(new VBox(8, new Label("Book Title:"), bookTitleField,
                new Label("Author:"), authorField,
                new Label("ISBN:"), isbnField,
                new Label("Book Added Date:"), datePicker));

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == editButtonType) {
                return List.of(bookTitleField.getText(), authorField.getText(), isbnField.getText(), datePicker.getValue().toString());
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
            File inputFile = new File(pathToCSV);
            File tempFile = new File("temp.csv");

            CSVReader reader = new CSVReader(new FileReader(inputFile));
            CSVWriter writer = new CSVWriter(new FileWriter(tempFile));

            List<String[]> allData = reader.readAll();
            for (String[] row : allData) {
                if (row[0].equals(oldBook.get(0)) && row[1].equals(oldBook.get(1)) && row[2].equals(oldBook.get(2)) && row[3].equals(oldBook.get(3))) {
                    writer.writeNext(new String[]{newBook.get(0), newBook.get(1), newBook.get(2), newBook.get(3)});
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
            File inputFile = new File(pathToCSV);
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
}
