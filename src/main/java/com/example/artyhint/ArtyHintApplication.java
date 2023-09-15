package com.example.artyhint;

import View.Gui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ArtyHintApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Gui myGui = new Gui();
        FXMLLoader fxmlLoader = new FXMLLoader(ArtyHintApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(myGui, 700, 700);
        stage.setTitle("WTAC v1.2");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}