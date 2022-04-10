package com.devcolibri.database;

import java.sql.*;
import java.time.LocalDate;

public class CRUD {
    private static final String ADD = "insert into fitnesclub (id_sub, visits) values(?,?)";
    private static final String SELECT = "select visits from fitnesclub where id_sub = ?";
    private static final String DELETE = "delete from fitnesclub WHERE id_sub = ? ";


    public static void main(String[] args) {
        LocalDate localDate = LocalDate.now();
        //add(54, Date.valueOf(localDate));
        select(12);
        //delete(54);
        //select();
    }

    public static void add (int id, Date date){
        DBWorker worker = new DBWorker();
        try {
            PreparedStatement preparedStatement = worker.getConnection().prepareStatement(ADD);
            preparedStatement.setInt(1, id);
            preparedStatement.setDate(2, date);
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0)
                System.out.println("Добавлен абонемент и дата посещения");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void select(int id){
        DBWorker worker = new DBWorker();
        try {
            PreparedStatement preparedStatement = worker.getConnection().prepareStatement(SELECT);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Абонемент: " + id);
            while (resultSet.next()){
                Date date = resultSet.getDate("visits");

                System.out.println("Посещение: " + date);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(int id){
        DBWorker worker = new DBWorker();
        try {
            PreparedStatement preparedStatement = worker.getConnection().prepareStatement(DELETE);
            preparedStatement.setInt(1,id);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0)
                System.out.println("Абонемент был удален");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
