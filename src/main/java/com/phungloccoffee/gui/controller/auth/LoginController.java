package com.phungloccoffee.gui.controller.auth;

import com.phungloccoffee.App;
import com.phungloccoffee.gui.model.AppUserSession;
import com.phungloccoffee.gui.service.RealAuthService;
import com.phungloccoffee.gui.service.SessionManager;
import com.phungloccoffee.util.AlertUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    private final RealAuthService authService = new RealAuthService();

    @FXML
    private void handleLogin() {
        try {
            String username = usernameField.getText() == null ? "" : usernameField.getText().trim();
            String password = passwordField.getText() == null ? "" : passwordField.getText();
            if (username.isEmpty() || password.isEmpty()) {
                AlertUtils.showError("Vui lòng nhập tài khoản và mật khẩu.");
                return;
            }

            Optional<AppUserSession> result = authService.login(username, password);
            if (result.isEmpty()) {
                AlertUtils.showError("Sai tài khoản hoặc mật khẩu.");
                return;
            }

            SessionManager.login(result.get());
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/phungloccoffee/gui/view/MainDashboard.fxml"));
            Scene scene = new Scene(loader.load(), 1280, 760);
            scene.getStylesheets().add(App.class.getResource("/com/phungloccoffee/gui/css/app.css").toExternalForm());
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Phụng Lộc Coffee - Dashboard");
        } catch (Exception e) {
            AlertUtils.showError(e.getMessage());
        }
    }

    @FXML
    private void handleForgotPassword(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/phungloccoffee/gui/view/auth/ForgotPassword.fxml"));
            Parent root = loader.load();

            Stage dialog = new Stage();
            dialog.setTitle("Khôi phục mật khẩu - Phụng Lộc Coffee");
            dialog.initOwner(((Node) event.getSource()).getScene().getWindow());
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setScene(new Scene(root));
            dialog.setResizable(false);
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtils.showError("Không thể mở form khôi phục mật khẩu.");
        }
    }
}
