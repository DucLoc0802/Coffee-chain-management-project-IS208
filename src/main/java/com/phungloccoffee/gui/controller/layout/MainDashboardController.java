package com.phungloccoffee.gui.controller.layout;

import com.phungloccoffee.util.AlertUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainDashboardController {
    @FXML private StackPane contentPane;
    @FXML private SidebarController sidebarController;

    @FXML
    private void initialize() {
        sidebarController.setDashboardController(this);
        loadContent("/com/phungloccoffee/gui/view/DashboardHome.fxml");
    }

    public void loadContent(String fxmlPath) {
        try {
            Node content = FXMLLoader.load(getClass().getResource(fxmlPath));
            contentPane.getChildren().setAll(content);
        } catch (IOException e) {
            AlertUtils.showError("Khong the mo man hinh: " + fxmlPath);
        }
    }
}
