package com.controller;

import com.view.ViewFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class AdminMainWindowController extends BaseController{
    public AdminMainWindowController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }

    @FXML
    BorderPane mainWindow;

    @FXML
    void itemsButtonAction(ActionEvent event) {
        System.out.println("Items Button Clicked!");

        viewFactory.showItemContent(mainWindow);
    }


    @FXML
    void suppliersButtonAction(ActionEvent event) {
        System.out.println("Suppliers Button Clicked!");

    }

    @FXML
    void sellButtonAction(ActionEvent event) {
        System.out.println("Sell Button Clicked!");

        viewFactory.showSellWindowContent(mainWindow);
    }

    @FXML
    void inventoryButtonAction(ActionEvent event) {
        System.out.println("Inventory Button Clicked!");
    }

    @FXML
    void usersButtonAction(ActionEvent event) {
        System.out.println("Users Button Action");

        viewFactory.showUsersWindowContent(mainWindow);
    }
}
