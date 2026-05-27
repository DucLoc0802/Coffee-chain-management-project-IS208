package com.phungloccoffee.gui.controller.auth;

import com.phungloccoffee.service.auth.ForgotPasswordService;
import com.phungloccoffee.service.auth.ForgotPasswordService.FindAccountResult;
import com.phungloccoffee.service.auth.ForgotPasswordService.ResetPasswordResult;
import com.phungloccoffee.service.auth.ForgotPasswordService.SendOtpResult;
import com.phungloccoffee.service.auth.ForgotPasswordService.VerifyOtpResult;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ForgotPasswordController {
    @FXML private StackPane stepContainer;

    @FXML private VBox findAccountStep;
    @FXML private VBox chooseMethodStep;
    @FXML private VBox verifyOtpStep;
    @FXML private VBox resetPasswordStep;
    @FXML private VBox successStep;

    @FXML private TextField identifierField;
    @FXML private RadioButton emailOption;
    @FXML private RadioButton smsOption;
    @FXML private ToggleGroup methodToggleGroup;

    @FXML private Label maskedEmailLabel;
    @FXML private Label maskedPhoneLabel;

    @FXML private TextField otpField;
    @FXML private Label resendTimerLabel;

    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField confirmPasswordField;

    @FXML private Label errorLabel;
    @FXML private Label successLabel;

    private final ForgotPasswordService forgotPasswordService = new ForgotPasswordService();

    private String currentUserId;
    private String selectedMethod;
    private String resetToken;
    private String maskedEmail;
    private String maskedPhone;
    private Timeline resendTimeline;
    private int resendSecondsLeft;

    @FXML
    private void initialize() {
        showOnly(findAccountStep);
        clearMessages();
        maskedEmailLabel.setText("-");
        maskedPhoneLabel.setText("-");
        resendTimerLabel.setText("");
    }

    @FXML
    private void handleFindAccount() {
        clearMessages();
        String identifier = identifierField.getText() == null ? "" : identifierField.getText().trim();
        if (identifier.isEmpty()) {
            showError("Vui lòng nhập tài khoản, email hoặc số điện thoại.");
            return;
        }

        try {
            FindAccountResult result = forgotPasswordService.findAccount(identifier);
            if (!result.success()) {
                showError(result.message());
                return;
            }

            currentUserId = result.userId();
            maskedEmail = result.maskedEmail();
            maskedPhone = result.maskedPhone();
            maskedEmailLabel.setText(result.emailAvailable() ? maskedEmail : "Chưa có email khôi phục");
            maskedPhoneLabel.setText(result.phoneAvailable() ? maskedPhone : "Chưa có số điện thoại khôi phục");
            emailOption.setDisable(!result.emailAvailable());
            smsOption.setDisable(!result.phoneAvailable());
            methodToggleGroup.selectToggle(null);
            if (result.emailAvailable()) {
                methodToggleGroup.selectToggle(emailOption);
            } else if (result.phoneAvailable()) {
                methodToggleGroup.selectToggle(smsOption);
            }
            showChooseMethodStep();
        } catch (Exception e) {
            showError("Không thể tìm tài khoản lúc này. Vui lòng thử lại sau.");
        }
    }

    @FXML
    private void handleSendOtp() {
        clearMessages();
        if (methodToggleGroup.getSelectedToggle() == null) {
            showError("Vui lòng chọn phương thức nhận mã.");
            return;
        }

        selectedMethod = methodToggleGroup.getSelectedToggle() == emailOption ? "EMAIL" : "SMS";
        try {
            SendOtpResult result = forgotPasswordService.sendOtp(currentUserId, selectedMethod);
            if (!result.success()) {
                showError(result.message());
                return;
            }
            showSuccess(result.message());
            otpField.clear();
            startResendTimer();
            showVerifyOtpStep();
        } catch (Exception e) {
            showError("Không thể gửi mã xác nhận. Vui lòng thử lại sau.");
        }
    }

    @FXML
    private void handleVerifyOtp() {
        clearMessages();
        String otp = otpField.getText() == null ? "" : otpField.getText().trim();
        if (otp.isEmpty()) {
            showError("Vui lòng nhập mã xác nhận.");
            return;
        }
        if (!isValidOtp(otp)) {
            showError("Mã xác nhận phải gồm đúng 6 chữ số.");
            return;
        }

        try {
            VerifyOtpResult result = forgotPasswordService.verifyOtp(currentUserId, selectedMethod, otp);
            if (!result.success()) {
                showError(result.message());
                return;
            }
            resetToken = result.resetToken();
            newPasswordField.clear();
            confirmPasswordField.clear();
            showResetPasswordStep();
        } catch (Exception e) {
            showError("Không thể xác nhận mã lúc này. Vui lòng thử lại sau.");
        }
    }

    @FXML
    private void handleResendOtp() {
        clearMessages();
        if (resendSecondsLeft > 0) {
            showError("Vui lòng chờ " + resendSecondsLeft + " giây trước khi gửi lại mã.");
            return;
        }
        if (selectedMethod == null || selectedMethod.isBlank()) {
            showChooseMethodStep();
            showError("Vui lòng chọn phương thức nhận mã.");
            return;
        }

        SendOtpResult result = forgotPasswordService.sendOtp(currentUserId, selectedMethod);
        if (!result.success()) {
            showError(result.message());
            return;
        }
        showSuccess(result.message());
        otpField.clear();
        startResendTimer();
    }

    @FXML
    private void handleResetPassword() {
        clearMessages();
        String newPassword = newPasswordField.getText() == null ? "" : newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText() == null ? "" : confirmPasswordField.getText();

        if (newPassword.isBlank()) {
            showError("Vui lòng nhập mật khẩu mới.");
            return;
        }
        if (!isValidPassword(newPassword)) {
            showError("Mật khẩu mới phải có tối thiểu 8 ký tự.");
            return;
        }
        if (!newPassword.equals(confirmPassword)) {
            showError("Mật khẩu nhập lại không trùng khớp.");
            return;
        }

        try {
            ResetPasswordResult result = forgotPasswordService.resetPassword(currentUserId, resetToken, newPassword);
            if (!result.success()) {
                showError(result.message());
                return;
            }
            showSuccessStep();
        } catch (Exception e) {
            showError("Không thể cập nhật mật khẩu lúc này. Vui lòng thử lại sau.");
        }
    }

    @FXML
    private void showFindAccountStep() {
        showOnly(findAccountStep);
    }

    @FXML
    private void showChooseMethodStep() {
        showOnly(chooseMethodStep);
    }

    @FXML
    private void showVerifyOtpStep() {
        showOnly(verifyOtpStep);
    }

    @FXML
    private void showResetPasswordStep() {
        showOnly(resetPasswordStep);
    }

    @FXML
    private void showSuccessStep() {
        showOnly(successStep);
        successLabel.setVisible(false);
        successLabel.setManaged(false);
    }

    @FXML
    private void handleClose() {
        if (resendTimeline != null) {
            resendTimeline.stop();
        }
        Stage stage = (Stage) stepContainer.getScene().getWindow();
        stage.close();
    }

    private void showOnly(VBox step) {
        clearMessages();
        findAccountStep.setVisible(false);
        findAccountStep.setManaged(false);
        chooseMethodStep.setVisible(false);
        chooseMethodStep.setManaged(false);
        verifyOtpStep.setVisible(false);
        verifyOtpStep.setManaged(false);
        resetPasswordStep.setVisible(false);
        resetPasswordStep.setManaged(false);
        successStep.setVisible(false);
        successStep.setManaged(false);

        step.setVisible(true);
        step.setManaged(true);

        if (successLabel != null && step != successStep) {
            successLabel.setVisible(true);
            successLabel.setManaged(true);
        }
    }

    private void showError(String message) {
        errorLabel.setText(message == null ? "" : message);
        successLabel.setText("");
    }

    private void showSuccess(String message) {
        successLabel.setVisible(true);
        successLabel.setManaged(true);
        successLabel.setText(message == null ? "" : message);
        errorLabel.setText("");
    }

    private void clearMessages() {
        if (errorLabel != null) {
            errorLabel.setText("");
        }
        if (successLabel != null) {
            successLabel.setText("");
        }
    }

    private boolean isValidOtp(String otp) {
        return otp != null && otp.matches("\\d{6}");
    }

    private boolean isValidPassword(String password) {
        return password != null && password.length() >= 8;
    }

    private void startResendTimer() {
        if (resendTimeline != null) {
            resendTimeline.stop();
        }
        resendSecondsLeft = 60;
        resendTimerLabel.setText("Gửi lại sau " + resendSecondsLeft + "s");
        resendTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            resendSecondsLeft--;
            if (resendSecondsLeft <= 0) {
                resendTimerLabel.setText("");
                resendTimeline.stop();
            } else {
                resendTimerLabel.setText("Gửi lại sau " + resendSecondsLeft + "s");
            }
        }));
        resendTimeline.setCycleCount(60);
        resendTimeline.playFromStart();
    }
}
