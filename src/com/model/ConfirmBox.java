package com.model;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {
    static boolean isTrue = false;

    public static boolean show(String title, String message) {
        Stage window = new Stage();


        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);

        Label label = new Label();
        label.setText(message);

        Button yesButton = new Button("Yes");
        yesButton.setPadding(new Insets(10, 15, 10, 15));
        yesButton.setOnAction(e -> {
            isTrue = true;
            window.close();
        });

        Button noButton = new Button("No");
        noButton.setPadding(new Insets(10, 15, 10, 15));
        noButton.setOnAction(e -> {
            isTrue = false;
            window.close();
        });

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(yesButton, noButton);
        HBox.setMargin(yesButton, new Insets(10, 0, 10, 0));
        HBox.setMargin(noButton, new Insets(0, 0, 0, 25));
        hBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(15);
        VBox.setMargin(label, new Insets(20, 10, 10, 10));
        vBox.getChildren().addAll(label, hBox);
        VBox.setMargin(yesButton, new Insets(0, 0, 12, 0));
        VBox.setMargin(hBox, new Insets(0, 0, 10, 0));
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.showAndWait();
        System.out.println(isTrue);
        return isTrue;
    }

}
