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

    public boolean executeQuery(String query) {
        boolean isSuccess = false;
        try {
            int result = statement.executeUpdate(query);
            if (result > 0) {
                System.out.println("Query Executed Successfully");
                isSuccess = true;
                connection.close();
            } else {
                System.out.println("Failed in executing query");
            }
        } catch (SQLException e) {
            System.out.println("Error in SQL: " + e);
        }
        return isSuccess;
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
        Item item;
        String itemCode, itemName, itemBrand, supplierName;
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
                item = new Item(itemCode, itemName, itemBrand, itemStock, itemRetailPrice, itemWholesalePrice, supplierId);
                itemObservableList.add(item);
            }
        } catch (SQLException e) {
            System.out.println("ERROR in executing SQL Query");
            e.printStackTrace();
        }
        return itemObservableList;
    }


    public ObservableList<User> getUserList() {
        ObservableList<User> userObservableList = FXCollections.observableArrayList();
        String username, password;
        boolean isAdmin;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users;");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                username = resultSet.getString("username");
                password = resultSet.getString("password");
                isAdmin = resultSet.getBoolean("is_admin");
                userObservableList.add(new User(username, password, isAdmin));
            }
        } catch (SQLException e) {
            System.out.println("ERROR in executing SQL Query");
            e.printStackTrace();
        }
        return userObservableList;
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

    public String getSupplierName(int id) {
        String supplierName = "";
        try {
            ResultSet resultSet = statement.executeQuery("SELECT name FROM suppliers WHERE id = " + id + ";");
            if (resultSet.next()) {
                supplierName = resultSet.getString("name");
            }
        } catch (SQLException e) {
            System.out.println("ERROR IN SQL: " + e);
        }
        return supplierName;
    }

    public boolean checkIfTaken(String query) {
        boolean isTaken = false;
        try {
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                isTaken = true;
            }
        } catch (SQLException e) {
            System.out.println("Error in SQL: " + e);
        }
        return isTaken;
    }

    public Item getItem(String code) {
        Item item = new Item();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM items WHERE code = '" + code + "';");
            if (resultSet.next()) {
                item.setCode(resultSet.getString("code"));
                item.setName(resultSet.getString("name"));
                item.setStock(resultSet.getInt("stock"));
                item.setRetailPrice(resultSet.getInt("retail_price"));
                item.setWholesalePrice(resultSet.getInt("wholesale_price"));
                item.setSupplierId(resultSet.getInt("supplier_id"));
            }
        } catch (SQLException e) {
            System.out.println("ERROR IN SQL: " + e);
        }
        return item;
    }

}
