package com.phungloccoffee.gui.controller.pos;

import com.phungloccoffee.util.AlertUtils;
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

public class OfflineTransactionController {
    @FXML private ComboBox<String> syncStatusComboBox;
    @FXML private ComboBox<String> deviceComboBox;
    @FXML private TableView<OfflineRow> offlineTable;
    @FXML private TableColumn<OfflineRow, String> transactionColumn;
    @FXML private TableColumn<OfflineRow, String> deviceColumn;
    @FXML private TableColumn<OfflineRow, String> cashierColumn;
    @FXML private TableColumn<OfflineRow, String> amountColumn;
    @FXML private TableColumn<OfflineRow, String> timeColumn;
    @FXML private TableColumn<OfflineRow, String> statusColumn;
    @FXML private TableColumn<OfflineRow, Void> actionColumn;

    @FXML
    private void initialize() {
        syncStatusComboBox.getItems().setAll("Tất cả trạng thái", "Chờ đồng bộ", "Đang đồng bộ", "Lỗi");
        deviceComboBox.getItems().setAll("Tất cả thiết bị", "POS001", "POS002", "POS008");
        syncStatusComboBox.getSelectionModel().selectFirst();
        deviceComboBox.getSelectionModel().selectFirst();

        transactionColumn.setCellValueFactory(new PropertyValueFactory<>("transactionCode"));
        deviceColumn.setCellValueFactory(new PropertyValueFactory<>("device"));
        cashierColumn.setCellValueFactory(new PropertyValueFactory<>("cashier"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusColumn.setCellFactory(column -> new StatusCell<>());
        actionColumn.setCellFactory(column -> new ActionCell());
        offlineTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        offlineTable.setItems(FXCollections.observableArrayList(
                new OfflineRow("OFF001", "POS001", "Nguyễn Thu Ngân", "185.000đ", "09:12 hôm nay", "Chờ đồng bộ"),
                new OfflineRow("OFF002", "POS002", "Trần Minh", "240.000đ", "09:25 hôm nay", "Đang đồng bộ"),
                new OfflineRow("OFF003", "POS008", "Lê Hoàng", "128.000đ", "10:04 hôm nay", "Chờ đồng bộ"),
                new OfflineRow("OFF004", "POS001", "Nguyễn Thu Ngân", "315.000đ", "10:21 hôm nay", "Lỗi")
        ));
    }

    @FXML
    private void syncSelected() {
        AlertUtils.showInfo("Đã gửi yêu cầu đồng bộ giao dịch offline.");
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
                case "Chờ đồng bộ" -> "status-warning";
                case "Đang đồng bộ" -> "status-info";
                default -> "status-danger";
            });
            setGraphic(badge);
            setText(null);
            setAlignment(Pos.CENTER_LEFT);
        }
    }

    private static class ActionCell extends TableCell<OfflineRow, Void> {
        private final HBox box = new HBox(8);
        private final Button viewButton = new Button("Xem");
        private final Button syncButton = new Button("Đồng bộ");

        ActionCell() {
            viewButton.getStyleClass().addAll("action-button", "action-view-button");
            syncButton.getStyleClass().addAll("action-button", "action-approve-button");
            box.setAlignment(Pos.CENTER_LEFT);
            box.getChildren().addAll(viewButton, syncButton);
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            setGraphic(empty ? null : box);
        }
    }

    public static class OfflineRow {
        private final String transactionCode;
        private final String device;
        private final String cashier;
        private final String amount;
        private final String time;
        private final String status;

        public OfflineRow(String transactionCode, String device, String cashier, String amount, String time, String status) {
            this.transactionCode = transactionCode;
            this.device = device;
            this.cashier = cashier;
            this.amount = amount;
            this.time = time;
            this.status = status;
        }

        public String getTransactionCode() {
            return transactionCode;
        }

        public String getDevice() {
            return device;
        }

        public String getCashier() {
            return cashier;
        }

        public String getAmount() {
            return amount;
        }

        public String getTime() {
            return time;
        }

        public String getStatus() {
            return status;
        }
    }
}
