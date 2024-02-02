package models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class CSVToDatabase {
    private static final String DATABASE_URL = "jdbc:mysql://bysja6c3ilckh00izaq5-mysql.services.clever-cloud.com:3306/bysja6c3ilckh00izaq5";
    private static final String USERNAME = "uz8aqhvsa1vsjzqw";
    private static final String PASSWORD = "PiMcFyLNwrtPRBPpJD77";
    private static final String TABLE_NAME = "Discos";

    public void insertDataFromCSV(String csvFilePath) {
        String sql = "INSERT INTO " + TABLE_NAME + " (columna1, columna2, columna3) VALUES (?, ?, ?, ?, ?, ?)";

        try (BufferedReader csvReader = new BufferedReader(new FileReader(csvFilePath));
             Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            String row;
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(","); // Asume que los datos están separados por comas
                preparedStatement.setString(1, data[4]);
                preparedStatement.setString(2, data[5]);
                preparedStatement.setString(3, data[6]);
                preparedStatement.setString(4, data[7]);
                preparedStatement.setString(5, data[8]);
                preparedStatement.setString(6, data[9]);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}