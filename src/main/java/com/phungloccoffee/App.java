package com.phungloccoffee;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class App extends Application {
    private static final String APP_CSS = "/com/phungloccoffee/gui/css/app.css";
    private static final String LOGIN_FXML = "/com/phungloccoffee/gui/view/Login.fxml";

    @Override
    public void start(Stage primaryStage) throws IOException {
        loadLocalFont("/com/phungloccoffee/gui/fonts/Inter-Regular.ttf");
        loadLocalFont("/com/phungloccoffee/gui/fonts/Inter-Bold.ttf");

        Parent root = FXMLLoader.load(App.class.getResource(LOGIN_FXML));
        Scene scene = new Scene(root, 1280, 720);
        scene.getStylesheets().add(App.class.getResource(APP_CSS).toExternalForm());
        primaryStage.setTitle("Phụng Lộc Coffee");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1100);
        primaryStage.setMinHeight(700);
        primaryStage.show();
    }

    private void loadLocalFont(String resourcePath) {
        try (InputStream fontStream = getClass().getResourceAsStream(resourcePath)) {
            if (fontStream != null) {
                Font.loadFont(fontStream, 14);
            }
        } catch (Exception ignored) {
            // Optional local fonts are ignored when unavailable; system fonts handle the UI.
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
