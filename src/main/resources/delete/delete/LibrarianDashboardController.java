package delete.delete;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class LibrarianDashboardController {

    public static void main(String[] args) {
        String csvFile = "data55555.csv";
        String[] header = {"Name", "Age", "Email"};
        String[][] data = {
                {"John Doe", "30", "john.doe@example.com"},
                {"Jane Smith", "25", "jane.smith@example.com"}
        };

        // Writing to CSV
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile))) {
            writer.writeNext(header);
            writer.writeAll(List.of(data));
            System.out.println("Data written to CSV file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Reading from CSV
        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            List<String[]> allData = reader.readAll();
            for (String[] row : allData) {
                System.out.println(String.join(", ", row));
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }
}
