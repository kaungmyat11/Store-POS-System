package com.controller;

import com.model.AlertBox;
import com.model.ConfirmBox;
import com.model.DbConnect;
import com.model.Item;
import com.view.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.w3c.dom.events.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminItemsWindowController extends BaseController implements Initializable {

    @FXML
    TableView itemsTableView;

    @FXML
    TableColumn codeColumn;

    @FXML
    TableColumn nameColumn;

    @FXML
    TableColumn stockColumn;

    @FXML
    TableColumn brandColumn;

    @FXML
    TableColumn retailColumn;

    @FXML
    TableColumn wholesaleColumn;

    @FXML
    TableColumn supplierColumn;

    @FXML
    TextField searchTextField;

    @FXML
    Button deleteButton;

    ObservableList<Item> itemObservableList = FXCollections.observableArrayList();

    Item selectedItem;

    public AdminItemsWindowController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }

    boolean isEmpty;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        codeColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("code"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("brand"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("stock"));
        retailColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("retailPrice"));
        wholesaleColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("wholesalePrice"));
        supplierColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("supplierName"));

        refreshItemTable();
    }

    public void refreshItemTable() {
        DbConnect dbConnect = new DbConnect();
        itemObservableList = dbConnect.getItemList();

        FilteredList<Item> filteredList = new FilteredList<>(itemObservableList, b -> true);
        searchTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredList.setPredicate(Item -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                if (Item.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(Item.getCode()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(Item.getStock()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        } );

        SortedList<Item> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(itemsTableView.comparatorProperty());
        itemsTableView.setItems(sortedList);
    }

    @FXML
    void addButtonAction(ActionEvent event) {
        System.out.println("Add Button Clicked");

        viewFactory.showAddItemWindow();
        refreshItemTable();
    }

    @FXML
    void deleteButtonAction(ActionEvent event) {
        System.out.println("Delete Button Clicked");

        isEmpty = itemsTableView.getSelectionModel().isEmpty();
        if (isEmpty) {
            showAlertBox();
        } else {
            selectedItem = (Item) itemsTableView.getSelectionModel().getSelectedItem();
            System.out.println(selectedItem.getName());
            boolean isTrue = ConfirmBox.show("Delete Item","Do you want to delete " + selectedItem.getName() + " ?");

            if (isTrue) {
                System.out.println("Delete the item from database.");
                DbConnect dbConnect = new DbConnect();
                dbConnect.executeQuery("DELETE FROM items WHERE code = '" + selectedItem.getCode() + "';");
                refreshItemTable();
            }
        }
    }

    @FXML
    void editButtonAction(ActionEvent event) {
        System.out.println("Edit Button Clicked");
        isEmpty = itemsTableView.getSelectionModel().isEmpty();
        System.out.println(isEmpty);
        if (isEmpty) {
            showAlertBox();
        } else {
            selectedItem = (Item) itemsTableView.getSelectionModel().getSelectedItem();
            System.out.println(selectedItem.getName());
            System.out.println(selectedItem.getSupplierId());

            viewFactory.showEditItemWindow(selectedItem);
            refreshItemTable();
//        }
        }
    }

    void showAlertBox() {
        System.out.println("Please select an item.");
        AlertBox.show("Warning", "Please select an item.");
    }
}
