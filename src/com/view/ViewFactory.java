package com.view;

import com.controller.*;
import com.model.Item;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ViewFactory {
    private ArrayList<Stage> activeStages;
    private Pane view;


    public ViewFactory() {
        activeStages = new ArrayList<>();
    }

    public void showLoginWindow() {
        System.out.println("Show Login Window");

        BaseController controller = new LoginWindowController(this, "LoginWindow.fxml");
        initializeStage(controller, "Login Window", false);
    }

    public void showAdminMainWindow() {
        System.out.println("Show Admin Main Window");

        BaseController controller = new AdminMainWindowController(this, "AdminMainWindow.fxml");
        initializeStage(controller, "Admin Main Window", false);
    }

    //Need to review this <This is not correct> *Important
    public void showUserMainWindow() {
        System.out.println("Show User Main Window");

        BaseController controller = new AdminMainWindowController(this, "AdminMainWindow.fxml");
        initializeStage(controller, "User Main Window", false);
    }

    public void showEditItemWindow(Item selectedItem) {
        System.out.println("Show Edit Item Window");

        BaseController controller = new EditItemWindowController(this, "EditItemWindow.fxml", selectedItem);
        initializeStage(controller, "Edit Item Window", true);

    }

    public void showItemContent(BorderPane pane) {
        System.out.println("Show Item Content");

        BaseController controller = new AdminItemsWindowController(this, "AdminItemsWindow.fxml");
        view = loadContent(controller);
        pane.setCenter(view);
    }

    public Pane loadContent(BaseController controller) {    //String fileName
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(controller.getFxmlName()));
            fxmlLoader.setController(controller);
            view = fxmlLoader.load();
        } catch (IOException e) {
            System.out.println("Error in getPage Method: " + e);
        }
        return view;
    }

    private void initializeStage(BaseController controller, String title, boolean hasToWait) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(controller.getFxmlName()));
        fxmlLoader.setController(controller);
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            System.out.println("ERROR in Rendering UI");
            e.printStackTrace();
            return;
        }

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(title);
        if (hasToWait) {
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } else {
            stage.show();
        }



        activeStages.add(stage);
    }

    public void closeStage(Stage stageToClose) {
        stageToClose.close();
        activeStages.remove(stageToClose);
    }

}
