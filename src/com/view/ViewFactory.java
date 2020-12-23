package com.view;

import com.controller.BaseController;
import com.controller.LoginWindowController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ViewFactory {
    private ArrayList<Stage> activeStages;


    public ViewFactory() {
        activeStages = new ArrayList<>();
    }

    public void showLoginWindow() {
        System.out.println("Show Login Window");

        BaseController controller = new LoginWindowController(this, "LoginWindow.fxml");
        initializeStage(controller);
    }

    private void initializeStage(BaseController controller) {
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
        stage.show();
        activeStages.add(stage);
    }

    public void closeStage(Stage stageToClose) {
        stageToClose.close();
        activeStages.remove(stageToClose);
    }
}
