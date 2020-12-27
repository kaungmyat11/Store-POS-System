package com.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.sql.*;
import java.util.ArrayList;

public class DbConnect {
    private Connection connection;
    private Statement statement;


    public DbConnect() {
        try {
            String URL = "jdbc:postgresql://localhost:5432/pos_system";
            String username = "postgres";
            String password = "admin";
            connection = DriverManager.getConnection(URL, username, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Connection failure!");
            e.printStackTrace();
        }
    }

    public void executeQuery(String query) {
        try {
            int result = statement.executeUpdate(query);
            if (result > 0) {
                System.out.println("Query Executed Successfully");
                connection.close();
            } else {
                System.out.println("Failed in executing query");
            }
        } catch (SQLException e) {
            System.out.println("Error in SQL: " + e);
        }
    }


    public boolean[] executeLoginQuery(String query) {
        boolean[] boolArray = new boolean[2];
        boolean isLogin = false;
        boolean isAdmin = false;
        try {
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                isLogin = true;
                isAdmin = resultSet.getBoolean(3);
                connection.close();
            } else {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("ERROR in SQL : " + e);
        }
        boolArray[0] = isLogin;
        boolArray[1] = isAdmin;
        return boolArray;
    }

    public ObservableList<Item> getItemList() {
        ObservableList<Item> itemObservableList = FXCollections.observableArrayList();
        String itemCode, itemName, itemBrand;
        int itemStock, itemRetailPrice, itemWholesalePrice, supplierId;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT  * FROM items;");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                itemCode = resultSet.getString("code");
                itemName = resultSet.getString("name");
                itemBrand = resultSet.getString("brand");
                itemStock = resultSet.getInt("stock");
                itemRetailPrice = resultSet.getInt("retail_price");
                itemWholesalePrice = resultSet.getInt("wholesale_price");
                supplierId = resultSet.getInt("supplier_id");
                itemObservableList.add(new Item(itemCode, itemName, itemBrand, itemStock, itemRetailPrice, itemWholesalePrice, supplierId));
            }
        } catch (SQLException e) {
            System.out.println("ERROR in executing SQL Query");
            e.printStackTrace();
        }
        return itemObservableList;
    }


    public ArrayList<String> getSupplierList() {
        String supplierName;
        ArrayList<String> supplierNameList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM suppliers;");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                supplierName = resultSet.getString("name");
                supplierNameList.add(supplierName);
            }
        } catch (SQLException e) {
            System.out.println("Error in SQL execution: " + e);
        }
        return supplierNameList;
    }

    public int getSupplierId(String supplierName) {
        int id = 0;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT id FROM suppliers WHERE name = '" + supplierName + "';");
            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("ERROR IN SQL: " + e);
        }
        return id;
    }

}
