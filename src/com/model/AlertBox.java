package com.model;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicBoolean;

public class AlertBox {

    public static void show(String title, String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);

        Button closeButton = new Button("Ok");
        closeButton.setOnAction(e -> window.close());

        //Add info image to the window if possible
//        File file = new File("/com/source/information-button.png");
//        Image image = new Image(file.toURI().toString());
//        ImageView imageView = new ImageView();
//        imageView.setImage(image);
//        imageView.setCache(true);

        VBox layout = new VBox(15);
        VBox.setMargin(label, new Insets(30, 10, 10, 10));
        layout.getChildren().addAll(label, closeButton);
        VBox.setMargin(closeButton, new Insets(0, 0, 12, 0));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }



}
