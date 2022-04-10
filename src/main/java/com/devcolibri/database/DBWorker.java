package com.devcolibri.database;

import java.sql.*;

public class DBWorker {

    private static final String URL = "jdbc:mysql://localhost:3306/dbfitnesclub";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private static Connection connection;

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Не удалось загрузить класс.");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
