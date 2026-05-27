package com.phungloccoffee.gui.controller.system;

import com.phungloccoffee.util.AlertUtils;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

public class SystemConfigController {
    @FXML private ComboBox<String> currencyComboBox;
    @FXML private ComboBox<String> auditCycleComboBox;
    @FXML private ComboBox<String> exportFormatComboBox;
    @FXML private CheckBox autoSyncCheckBox;

    @FXML
    private void initialize() {
        currencyComboBox.getItems().setAll("VNĐ", "USD");
        currencyComboBox.setValue("VNĐ");
        auditCycleComboBox.getItems().setAll("Hàng tuần", "Hàng tháng", "Hàng quý");
        auditCycleComboBox.setValue("Hàng tháng");
        exportFormatComboBox.getItems().setAll("Excel", "PDF", "Excel/PDF");
        exportFormatComboBox.setValue("Excel/PDF");
        autoSyncCheckBox.setSelected(true);
    }

    @FXML
    private void saveConfig() {
        AlertUtils.showInfo("Đã lưu cấu hình giao diện.");
    }

    @FXML
    private void resetConfig() {
        currencyComboBox.setValue("VNĐ");
        auditCycleComboBox.setValue("Hàng tháng");
        exportFormatComboBox.setValue("Excel/PDF");
        autoSyncCheckBox.setSelected(true);
        AlertUtils.showInfo("Đã khôi phục cấu hình mặc định trên giao diện.");
    }

    @FXML
    private void reloadConfig() {
        AlertUtils.showInfo("Đã tải lại cấu hình.");
    }
}
