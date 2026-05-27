package com.phungloccoffee.gui.controller.customer;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class CustomerListController {
    @FXML private TextField searchField;
    @FXML private ComboBox<String> tierFilterComboBox;
    @FXML private ComboBox<String> statusFilterComboBox;
    @FXML private TableView<CustomerRow> customerTable;
    @FXML private TableColumn<CustomerRow, String> codeColumn;
    @FXML private TableColumn<CustomerRow, String> nameColumn;
    @FXML private TableColumn<CustomerRow, String> phoneColumn;
    @FXML private TableColumn<CustomerRow, String> emailColumn;
    @FXML private TableColumn<CustomerRow, String> tierColumn;
    @FXML private TableColumn<CustomerRow, Integer> pointsColumn;
    @FXML private TableColumn<CustomerRow, String> statusColumn;
    @FXML private TableColumn<CustomerRow, Void> actionColumn;

    @FXML
    private void initialize() {
        tierFilterComboBox.getItems().setAll("Tất cả hạng", "Gold", "Silver", "Member");
        statusFilterComboBox.getItems().setAll("Tất cả trạng thái", "Hoạt động", "Tạm khóa");
        tierFilterComboBox.getSelectionModel().selectFirst();
        statusFilterComboBox.getSelectionModel().selectFirst();

        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        tierColumn.setCellValueFactory(new PropertyValueFactory<>("tier"));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusColumn.setCellFactory(column -> new StatusCell<>());
        actionColumn.setCellFactory(column -> new ActionCell());
        customerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        loadCustomers();
    }

    private void loadCustomers() {
        customerTable.setItems(FXCollections.observableArrayList(
                new CustomerRow("KH001", "Nguyễn Minh Anh", "0901000001", "minh.anh@email.com", "Gold", 1250, "Hoạt động"),
                new CustomerRow("KH002", "Trần Quốc Bảo", "0901000002", "bao.tran@email.com", "Silver", 680, "Hoạt động"),
                new CustomerRow("KH003", "Lê Hoàng Yến", "0901000003", "yen.le@email.com", "Member", 120, "Hoạt động"),
                new CustomerRow("KH004", "Phạm Gia Hân", "0901000004", "han.pham@email.com", "Gold", 1420, "Tạm khóa")
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
            badge.getStyleClass().addAll("status-badge", "Hoạt động".equals(status) ? "status-success" : "status-warning");
            setGraphic(badge);
            setText(null);
            setAlignment(Pos.CENTER_LEFT);
        }
    }

    private static class ActionCell extends TableCell<CustomerRow, Void> {
        private final HBox box = new HBox(8);
        private final Button viewButton = new Button("Xem");
        private final Button editButton = new Button("Sửa");
        private final Button lockButton = new Button();

        ActionCell() {
            viewButton.getStyleClass().addAll("action-button", "action-view-button");
            editButton.getStyleClass().addAll("action-button", "action-edit-button");
            lockButton.getStyleClass().addAll("action-button", "action-lock-button");
            box.setAlignment(Pos.CENTER_LEFT);
            box.getChildren().addAll(viewButton, editButton, lockButton);
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                setGraphic(null);
                return;
            }
            CustomerRow row = getTableRow().getItem();
            lockButton.setText("Tạm khóa".equals(row.getStatus()) ? "Mở" : "Khóa");
            lockButton.getStyleClass().removeAll("action-lock-button", "action-approve-button");
            lockButton.getStyleClass().add("Tạm khóa".equals(row.getStatus()) ? "action-approve-button" : "action-lock-button");
            setGraphic(box);
        }
    }

    public static class CustomerRow {
        private final String code;
        private final String name;
        private final String phone;
        private final String email;
        private final String tier;
        private final int points;
        private final String status;

        public CustomerRow(String code, String name, String phone, String email, String tier, int points, String status) {
            this.code = code;
            this.name = name;
            this.phone = phone;
            this.email = email;
            this.tier = tier;
            this.points = points;
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public String getPhone() {
            return phone;
        }

        public String getEmail() {
            return email;
        }

        public String getTier() {
            return tier;
        }

        public int getPoints() {
            return points;
        }

        public String getStatus() {
            return status;
        }
    }
}
