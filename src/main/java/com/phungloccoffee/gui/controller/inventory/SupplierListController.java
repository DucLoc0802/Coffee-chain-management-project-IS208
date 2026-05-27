package com.phungloccoffee.gui.controller.inventory;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class SupplierListController {
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private ComboBox<String> statusComboBox;
    @FXML private TableView<SupplierRow> supplierTable;
    @FXML private TableColumn<SupplierRow, String> codeColumn;
    @FXML private TableColumn<SupplierRow, String> nameColumn;
    @FXML private TableColumn<SupplierRow, String> categoryColumn;
    @FXML private TableColumn<SupplierRow, String> contactColumn;
    @FXML private TableColumn<SupplierRow, String> phoneColumn;
    @FXML private TableColumn<SupplierRow, String> statusColumn;
    @FXML private TableColumn<SupplierRow, Void> actionColumn;

    @FXML
    private void initialize() {
        categoryComboBox.getItems().setAll("Tất cả nhóm", "Cà phê", "Sữa", "Bao bì", "Syrup");
        statusComboBox.getItems().setAll("Tất cả trạng thái", "Đang hợp tác", "Tạm dừng");
        categoryComboBox.getSelectionModel().selectFirst();
        statusComboBox.getSelectionModel().selectFirst();

        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusColumn.setCellFactory(column -> new StatusCell<>());
        actionColumn.setCellFactory(column -> new ActionCell());
        supplierTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        supplierTable.setItems(FXCollections.observableArrayList(
                new SupplierRow("NCC001", "Công ty cà phê Phụng Lộc", "Cà phê", "Nguyễn Minh", "0902000001", "Đang hợp tác"),
                new SupplierRow("NCC002", "Vinamilk Food Service", "Sữa", "Trần Lan", "0902000002", "Đang hợp tác"),
                new SupplierRow("NCC003", "Bao bì An Phát", "Bao bì", "Lê Tuấn", "0902000003", "Đang hợp tác"),
                new SupplierRow("NCC004", "Sweet Syrup Việt Nam", "Syrup", "Phạm Huy", "0902000004", "Đang hợp tác"),
                new SupplierRow("NCC005", "Nguyên liệu Á Châu", "Khác", "Đỗ An", "0902000005", "Tạm dừng")
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
            badge.getStyleClass().addAll("status-badge", "Đang hợp tác".equals(status) ? "status-success" : "status-warning");
            setGraphic(badge);
            setText(null);
            setAlignment(Pos.CENTER_LEFT);
        }
    }

    private static class ActionCell extends TableCell<SupplierRow, Void> {
        private final HBox box = new HBox(8);
        private final Button viewButton = new Button("Xem");
        private final Button editButton = new Button("Sửa");

        ActionCell() {
            viewButton.getStyleClass().addAll("action-button", "action-view-button");
            editButton.getStyleClass().addAll("action-button", "action-edit-button");
            box.setAlignment(Pos.CENTER_LEFT);
            box.getChildren().addAll(viewButton, editButton);
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            setGraphic(empty ? null : box);
        }
    }

    public static class SupplierRow {
        private final String code;
        private final String name;
        private final String category;
        private final String contact;
        private final String phone;
        private final String status;

        public SupplierRow(String code, String name, String category, String contact, String phone, String status) {
            this.code = code;
            this.name = name;
            this.category = category;
            this.contact = contact;
            this.phone = phone;
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public String getCategory() {
            return category;
        }

        public String getContact() {
            return contact;
        }

        public String getPhone() {
            return phone;
        }

        public String getStatus() {
            return status;
        }
    }
}
