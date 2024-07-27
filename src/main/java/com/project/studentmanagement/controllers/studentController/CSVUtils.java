package com.project.studentmanagement.controllers.studentController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVUtils {

    public static void writeDataToCSV(String filePath, List<String> data) throws IOException {
        try (FileWriter writer = new FileWriter(filePath, true)) {  // 'true' for appending data
            for (String field : data) {
                writer.append(quoteField(field)).append(",");
            }
            writer.append("\n");
        }
    }

    public static List<List<String>> readDataFromCSV(String filePath) throws IOException {
        List<List<String>> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); // Handle quoted commas
                List<String> row = new ArrayList<>();
                for (String field : fields) {
                    row.add(unquoteField(field));
                }
                data.add(row);
            }
        }
        return data;
    }

    public static void updateCSV(String filePath, String studentId, List<String> newData) throws IOException {
        List<List<String>> data = readDataFromCSV(filePath);
        try (FileWriter writer = new FileWriter(filePath)) {
            for (List<String> row : data) {
                if (row.get(0).equals(studentId)) {
                    row = newData;  // Update row data
                }
                for (String field : row) {
                    writer.append(quoteField(field)).append(",");
                }
                writer.append("\n");
            }
        }
    }

    public static void deleteFromCSV(String filePath, String studentId) throws IOException {
        List<List<String>> data = readDataFromCSV(filePath);
        try (FileWriter writer = new FileWriter(filePath)) {
            for (List<String> row : data) {
                if (!row.get(0).equals(studentId)) {
                    for (String field : row) {
                        writer.append(quoteField(field)).append(",");
                    }
                    writer.append("\n");
                }
            }
        }
    }

    private static String quoteField(String field) {
        return "\"" + field.replace("\"", "\"\"") + "\"";
    }

    private static String unquoteField(String field) {
        if (field.startsWith("\"") && field.endsWith("\"")) {
            field = field.substring(1, field.length() - 1);
        }
        return field.replace("\"\"", "\"");
    }
}
