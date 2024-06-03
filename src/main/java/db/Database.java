package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection connection = null;
    private static Database instance = null;

    private Database() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/hr_db", "student", "student");
            if(connection != null)
                System.out.println("Connected to the database!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static Database getInstance()
    {
        if (instance == null)
        {
            instance = new Database();
        }
        return instance;
    }
    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAllData() {
        try {
            connection.createStatement().execute("DELETE FROM residents_specialization");
            connection.createStatement().execute("DELETE FROM residents");
            connection.createStatement().execute("DELETE FROM specializations");
            connection.createStatement().execute("DELETE FROM hospital_specialization");
            connection.createStatement().execute("DELETE FROM hospitals");
            connection.createStatement().execute("DELETE FROM matchings");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteMatchings(){
        try {
            connection.createStatement().execute("DELETE FROM matchings");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}