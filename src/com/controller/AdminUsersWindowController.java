package com.controller;

import com.model.*;
import com.view.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminUsersWindowController extends BaseController implements Initializable {
    public AdminUsersWindowController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }

    @FXML
    private TextField searchTextField;

    @FXML
    private TableView userTableView;

    @FXML
    private TableColumn usernameTableColumn;

    @FXML
    private TableColumn passwordTableColumn;

    @FXML
    private TableColumn isAdminTableColumn;

    ObservableList<User> userObservableList = FXCollections.observableArrayList();

    boolean isEmpty;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameTableColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        passwordTableColumn.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        isAdminTableColumn.setCellValueFactory(new PropertyValueFactory<User, Boolean>("isAdmin"));

        refreshUserTable();
    }

    public void refreshUserTable() {
        DbConnect dbConnect = new DbConnect();
        userObservableList = dbConnect.getUserList();

        FilteredList<User> filteredList = new FilteredList<>(userObservableList, b -> true);
        searchTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredList.setPredicate(User -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                if (User.getUsername().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(User.getPassword()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(User.getIsAdmin()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<User> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(userTableView.comparatorProperty());
        userTableView.setItems(sortedList);
    }


    @FXML
    void addButtonAction(ActionEvent event) {
        System.out.println("Add Button Clicked");
    }

    @FXML
    void editButtonAction(ActionEvent event) {
        System.out.println("Edit Button Clicked");
    }

    @FXML
    void deleteButtonAction(ActionEvent event) {
        System.out.println("Delete Button Clicked");

        isEmpty = userTableView.getSelectionModel().isEmpty();

        if (isEmpty) {
            AlertBox.show("Warning", "Please select a user.");
        } else {
            User selectedUser = (User) userTableView.getSelectionModel().getSelectedItem();
            boolean isConfirm = ConfirmBox.show("Confirm Delete", "Are you sure to delete " + selectedUser.getUsername() + " ?");
            if (isConfirm) {
                DbConnect dbConnect = new DbConnect();
                String query = "DELETE FROM users WHERE username = '" + selectedUser.getUsername() + "';";
                boolean isDeleted = dbConnect.executeQuery(query);
                if (isDeleted) {
                    AlertBox.show("Success", "Deleted Successfully");
                    refreshUserTable();
                }
            }
        }

    }
}
