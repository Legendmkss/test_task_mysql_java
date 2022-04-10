package com.devcolibri.database;

import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Main {
    private static final String SELECT_VISITS = "select visits from fitnesclub where id_sub = ?";
    private static final String SELECT_ID = "select * from fitnesclub";

    public static void main(String[] args) throws ParseException {
        Date dateA = new SimpleDateFormat("yyyy-MM-dd").parse("2020-09-01");
        Date dateB = new SimpleDateFormat("yyyy-MM-dd").parse("2021-10-31");
        getVisitList(411, dateA, dateB); // Возможность получить список посещений за период по абонементу
        //getDiscount(25); // Скидка при продлении абонемента 5%, если за год было 10 посещений
    }

    public static void getVisitList(int id, Date dateAfter, Date dateBefore) {

        DBWorker worker = new DBWorker();
        PreparedStatement preparedStatement;
        ArrayList<Integer> id_DateBase = new ArrayList<>();

        try {
            Statement statement = worker.getConnection().createStatement();
            ResultSet resultId = statement.executeQuery(SELECT_ID);
            while (resultId.next()) {
                id_DateBase.add(resultId.getInt(1));}

            if (id_DateBase.contains(id)){
                try {
                    preparedStatement = worker.getConnection().prepareStatement(SELECT_VISITS);
                    preparedStatement.setInt(1,id);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    int count = 0;
                    while (resultSet.next()){
                        Date visits = resultSet.getDate("visits");
                        if (!(visits == null) && visits.after(dateAfter) && visits.before(dateBefore)) {
                            System.out.println("Вы посетили клуб: " + visits + ".");
                            count++;
                        }
                    }

                    if (count == 0)
                        System.out.println("В этот период вы не посещали клуб.");

                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (NullPointerException e){
                    System.out.println("В этот период вы не посещали клуб.");
                }
            } else System.out.println("Данного абонемента нет в базе данных.");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public static void getDiscount(int id){
        DBWorker worker = new DBWorker();
        PreparedStatement preparedStatement;
        Date now_year = new Date();
        ArrayList<Integer> id_DateBase = new ArrayList<>();
        try {
            Statement statement = worker.getConnection().createStatement();
            ResultSet resultId = statement.executeQuery(SELECT_ID);
            while (resultId.next()) {
                id_DateBase.add(resultId.getInt(1));}

            if (id_DateBase.contains(id)) {
                try {
                    preparedStatement = worker.getConnection().prepareStatement(SELECT_VISITS);
                    preparedStatement.setInt(1, id);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    int count = 0;
                    while (resultSet.next()) {
                        Date visits = resultSet.getDate("visits");
                        if (!(visits == null) && visits.getYear() == now_year.getYear())
                            count++;
                    }
                    if (count >= 10){
                        System.out.println("Количество ваших посещений: " + count + ". Ваша скидка 5%.");
                    } else {
                        System.out.println("Количество ваших посещений: " + count + ". К сожалению у вас нет скидки.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else System.out.println("Данного абонемента нет в базе данных.");
        }catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
