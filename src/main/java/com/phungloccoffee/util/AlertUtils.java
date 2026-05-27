package com.phungloccoffee.util;

import javafx.scene.control.Alert;

public final class AlertUtils {
    private AlertUtils() {
    }

    public static void showInfo(String message) {
        show(Alert.AlertType.INFORMATION, "Thong bao", message);
    }

    public static void showError(String message) {
        show(Alert.AlertType.ERROR, "Loi", message);
    }

    public static void showWarning(String message) {
        show(Alert.AlertType.WARNING, "Canh bao", message);
    }

    private static void show(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
