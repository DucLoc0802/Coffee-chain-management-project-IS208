package com.phungloccoffee.gui.controller.system;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PosDeviceManagementController {
    @FXML private ComboBox<String> branchComboBox;
    @FXML private ComboBox<String> statusComboBox;
    @FXML private TableView<DeviceRow> deviceTable;
    @FXML private TableColumn<DeviceRow, String> deviceCodeColumn;
    @FXML private TableColumn<DeviceRow, String> deviceNameColumn;
    @FXML private TableColumn<DeviceRow, String> branchColumn;
    @FXML private TableColumn<DeviceRow, String> serialColumn;
    @FXML private TableColumn<DeviceRow, String> lastOnlineColumn;
    @FXML private TableColumn<DeviceRow, String> statusColumn;

    @FXML
    private void initialize() {
        branchComboBox.getItems().setAll("Tất cả chi nhánh", "CN001", "CN002", "CN003", "CN004", "CN005", "CN006", "CN007", "CN008");
        statusComboBox.getItems().setAll("Tất cả trạng thái", "Online", "Đồng bộ chậm", "Offline");
        branchComboBox.getSelectionModel().selectFirst();
        statusComboBox.getSelectionModel().selectFirst();

        deviceCodeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        deviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        branchColumn.setCellValueFactory(new PropertyValueFactory<>("branch"));
        serialColumn.setCellValueFactory(new PropertyValueFactory<>("serial"));
        lastOnlineColumn.setCellValueFactory(new PropertyValueFactory<>("lastOnline"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusColumn.setCellFactory(column -> new StatusCell<>());
        deviceTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        deviceTable.setItems(FXCollections.observableArrayList(
                new DeviceRow("POS001", "Máy POS quầy 1", "CN001", "192.168.1.21 / PL-POS-001", "10:42 hôm nay", "Online"),
                new DeviceRow("POS002", "Máy POS quầy 2", "CN001", "192.168.1.22 / PL-POS-002", "10:40 hôm nay", "Online"),
                new DeviceRow("POS008", "Máy POS Phú Nhuận", "CN002", "192.168.2.18 / PL-POS-008", "10:38 hôm nay", "Online"),
                new DeviceRow("POS015", "Máy POS Thủ Đức", "CN005", "192.168.5.11 / PL-POS-015", "09:55 hôm nay", "Đồng bộ chậm"),
                new DeviceRow("POS024", "Máy POS Quận 10", "CN008", "192.168.8.19 / PL-POS-024", "08:10 hôm nay", "Offline")
        ));
    }

    private static class StatusCell<T> extends TableCell<T, String> {
        @Override
        protected void updateItem(String status, boolean empty) {
            super.updateItem(status, empty);
            if (empty || status == null) {
                setGraphic(null);
                setText(null);
                return;
            }
            Label badge = new Label(status);
            badge.getStyleClass().addAll("status-badge", switch (status) {
                case "Online" -> "status-success";
                case "Đồng bộ chậm" -> "status-warning";
                default -> "status-danger";
            });
            setGraphic(badge);
            setText(null);
            setAlignment(Pos.CENTER_LEFT);
        }
    }

    public static class DeviceRow {
        private final String code;
        private final String name;
        private final String branch;
        private final String serial;
        private final String lastOnline;
        private final String status;

        public DeviceRow(String code, String name, String branch, String serial, String lastOnline, String status) {
            this.code = code;
            this.name = name;
            this.branch = branch;
            this.serial = serial;
            this.lastOnline = lastOnline;
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public String getBranch() {
            return branch;
        }

        public String getSerial() {
            return serial;
        }

        public String getLastOnline() {
            return lastOnline;
        }

        public String getStatus() {
            return status;
        }
    }
}
