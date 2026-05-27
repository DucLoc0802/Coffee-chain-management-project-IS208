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

public class InventoryListController {
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private ComboBox<String> statusComboBox;
    @FXML private TableView<InventoryRow> inventoryTable;
    @FXML private TableColumn<InventoryRow, String> codeColumn;
    @FXML private TableColumn<InventoryRow, String> nameColumn;
    @FXML private TableColumn<InventoryRow, String> categoryColumn;
    @FXML private TableColumn<InventoryRow, String> stockColumn;
    @FXML private TableColumn<InventoryRow, String> warningColumn;
    @FXML private TableColumn<InventoryRow, String> statusColumn;
    @FXML private TableColumn<InventoryRow, Void> actionColumn;

    @FXML
    private void initialize() {
        categoryComboBox.getItems().setAll("Tất cả nhóm", "Cà phê", "Sữa", "Bao bì", "Syrup", "Khác");
        statusComboBox.getItems().setAll("Tất cả trạng thái", "Ổn định", "Tồn thấp", "Hết hàng");
        categoryComboBox.getSelectionModel().selectFirst();
        statusComboBox.getSelectionModel().selectFirst();

        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        warningColumn.setCellValueFactory(new PropertyValueFactory<>("warning"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusColumn.setCellFactory(column -> new StatusCell<>());
        actionColumn.setCellFactory(column -> new ActionCell());
        inventoryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        inventoryTable.setItems(FXCollections.observableArrayList(
                new InventoryRow("NL001", "Cà phê rang xay", "Cà phê", "34 kg", "20 kg", "Ổn định"),
                new InventoryRow("NL002", "Sữa tươi không đường", "Sữa", "12 lít", "15 lít", "Tồn thấp"),
                new InventoryRow("NL003", "Đường cát trắng", "Khác", "25 kg", "10 kg", "Ổn định"),
                new InventoryRow("NL004", "Ly giấy M", "Bao bì", "420 cái", "200 cái", "Ổn định"),
                new InventoryRow("NL005", "Bột matcha", "Khác", "0 kg", "5 kg", "Hết hàng")
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
                case "Ổn định" -> "status-success";
                case "Tồn thấp" -> "status-warning";
                default -> "status-danger";
            });
            setGraphic(badge);
            setText(null);
            setAlignment(Pos.CENTER_LEFT);
        }
    }

    private static class ActionCell extends TableCell<InventoryRow, Void> {
        private final Button button = new Button("Cập nhật");

        ActionCell() {
            button.getStyleClass().addAll("action-button", "action-edit-button");
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            setGraphic(empty ? null : button);
        }
    }

    public static class InventoryRow {
        private final String code;
        private final String name;
        private final String category;
        private final String stock;
        private final String warning;
        private final String status;

        public InventoryRow(String code, String name, String category, String stock, String warning, String status) {
            this.code = code;
            this.name = name;
            this.category = category;
            this.stock = stock;
            this.warning = warning;
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

        public String getStock() {
            return stock;
        }

        public String getWarning() {
            return warning;
        }

        public String getStatus() {
            return status;
        }
    }
}
