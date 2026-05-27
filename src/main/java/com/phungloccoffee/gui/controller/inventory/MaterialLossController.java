package com.phungloccoffee.gui.controller.inventory;

import com.phungloccoffee.util.AlertUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MaterialLossController {
    @FXML private ComboBox<String> materialComboBox;
    @FXML private TextField quantityField;
    @FXML private TextArea reasonArea;
    @FXML private TableView<LossRow> lossTable;
    @FXML private TableColumn<LossRow, String> codeColumn;
    @FXML private TableColumn<LossRow, String> materialColumn;
    @FXML private TableColumn<LossRow, String> quantityColumn;
    @FXML private TableColumn<LossRow, String> reasonColumn;
    @FXML private TableColumn<LossRow, String> statusColumn;

    private final ObservableList<LossRow> losses = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        materialComboBox.getItems().setAll("Cà phê rang xay", "Sữa tươi không đường", "Ly giấy M", "Syrup đào");
        materialComboBox.getSelectionModel().selectFirst();
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        materialColumn.setCellValueFactory(new PropertyValueFactory<>("material"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        reasonColumn.setCellValueFactory(new PropertyValueFactory<>("reason"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusColumn.setCellFactory(column -> new StatusCell<>());
        lossTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        losses.setAll(
                new LossRow("HH001", "Sữa tươi không đường", "2 lít", "Rách bao bì khi nhận hàng", "Chờ duyệt"),
                new LossRow("HH002", "Ly giấy M", "30 cái", "Móp méo trong vận chuyển", "Đã duyệt"),
                new LossRow("HH003", "Syrup đào", "1 chai", "Vỡ nắp chai", "Chờ duyệt")
        );
        lossTable.setItems(losses);
    }

    @FXML
    private void recordLoss() {
        String material = materialComboBox.getValue();
        String quantity = quantityField.getText() == null || quantityField.getText().isBlank() ? "0" : quantityField.getText();
        String reason = reasonArea.getText() == null || reasonArea.getText().isBlank() ? "Ghi nhận thủ công" : reasonArea.getText();
        losses.add(0, new LossRow("HH" + String.format("%03d", losses.size() + 1), material, quantity, reason, "Chờ duyệt"));
        quantityField.clear();
        reasonArea.clear();
        AlertUtils.showInfo("Đã ghi nhận hao hụt trên giao diện.");
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
            badge.getStyleClass().addAll("status-badge", "Đã duyệt".equals(status) ? "status-success" : "status-warning");
            setGraphic(badge);
            setText(null);
            setAlignment(Pos.CENTER_LEFT);
        }
    }

    public static class LossRow {
        private final String code;
        private final String material;
        private final String quantity;
        private final String reason;
        private final String status;

        public LossRow(String code, String material, String quantity, String reason, String status) {
            this.code = code;
            this.material = material;
            this.quantity = quantity;
            this.reason = reason;
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public String getMaterial() {
            return material;
        }

        public String getQuantity() {
            return quantity;
        }

        public String getReason() {
            return reason;
        }

        public String getStatus() {
            return status;
        }
    }
}
