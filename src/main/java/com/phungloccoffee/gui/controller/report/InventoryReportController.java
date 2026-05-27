package com.phungloccoffee.gui.controller.report;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class InventoryReportController {
    @FXML private ComboBox<String> branchFilterComboBox;
    @FXML private ComboBox<String> categoryFilterComboBox;
    @FXML private ComboBox<String> statusFilterComboBox;
    @FXML private BarChart<String, Number> branchWarningChart;
    @FXML private PieChart categoryPieChart;
    @FXML private TableView<InventoryRow> inventoryTable;
    @FXML private TableColumn<InventoryRow, String> branchColumn;
    @FXML private TableColumn<InventoryRow, String> trackedColumn;
    @FXML private TableColumn<InventoryRow, String> lowStockColumn;
    @FXML private TableColumn<InventoryRow, String> outOfStockColumn;
    @FXML private TableColumn<InventoryRow, String> valueColumn;
    @FXML private TableColumn<InventoryRow, String> statusColumn;

    @FXML
    private void initialize() {
        branchFilterComboBox.getItems().setAll("Tất cả chi nhánh", "CN001", "CN002", "CN003", "CN004", "CN005", "CN006", "CN007", "CN008");
        categoryFilterComboBox.getItems().setAll("Tất cả nhóm", "Cà phê", "Sữa", "Bao bì", "Syrup", "Khác");
        statusFilterComboBox.getItems().setAll("Tất cả trạng thái", "Ổn định", "Tồn thấp", "Hết hàng");
        branchFilterComboBox.getSelectionModel().selectFirst();
        categoryFilterComboBox.getSelectionModel().selectFirst();
        statusFilterComboBox.getSelectionModel().selectFirst();

        seedCharts();
        branchColumn.setCellValueFactory(new PropertyValueFactory<>("branch"));
        trackedColumn.setCellValueFactory(new PropertyValueFactory<>("tracked"));
        lowStockColumn.setCellValueFactory(new PropertyValueFactory<>("lowStock"));
        outOfStockColumn.setCellValueFactory(new PropertyValueFactory<>("outOfStock"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("inventoryValue"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusColumn.setCellFactory(column -> new StatusCell<>());
        inventoryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        inventoryTable.setItems(FXCollections.observableArrayList(
                new InventoryRow("CN001 - Trung tâm", "58", "1", "0", "260M", "Ổn định"),
                new InventoryRow("CN002 - Phú Nhuận", "54", "2", "0", "238M", "Ổn định"),
                new InventoryRow("CN003 - Bình Thạnh", "56", "4", "1", "221M", "Cần kiểm tra"),
                new InventoryRow("CN004 - Gò Vấp", "52", "2", "0", "205M", "Ổn định"),
                new InventoryRow("CN005 - Thủ Đức", "55", "5", "1", "196M", "Cảnh báo"),
                new InventoryRow("CN006 - Tân Bình", "51", "1", "0", "188M", "Ổn định"),
                new InventoryRow("CN007 - Quận 7", "50", "0", "0", "176M", "Ổn định"),
                new InventoryRow("CN008 - Quận 10", "52", "3", "2", "165M", "Cảnh báo")
        ));
    }

    private void seedCharts() {
        XYChart.Series<String, Number> warnings = new XYChart.Series<>();
        warnings.getData().addAll(
                new XYChart.Data<>("CN001", 1),
                new XYChart.Data<>("CN002", 2),
                new XYChart.Data<>("CN003", 4),
                new XYChart.Data<>("CN004", 2),
                new XYChart.Data<>("CN005", 5),
                new XYChart.Data<>("CN006", 1),
                new XYChart.Data<>("CN007", 0),
                new XYChart.Data<>("CN008", 3)
        );
        branchWarningChart.getData().setAll(warnings);
        categoryPieChart.setData(FXCollections.observableArrayList(
                new PieChart.Data("Cà phê", 32),
                new PieChart.Data("Sữa", 24),
                new PieChart.Data("Bao bì", 21),
                new PieChart.Data("Syrup", 13),
                new PieChart.Data("Khác", 10)
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
                case "Cần kiểm tra" -> "status-warning";
                default -> "status-danger";
            });
            setGraphic(badge);
            setText(null);
            setAlignment(Pos.CENTER_LEFT);
        }
    }

    public static class InventoryRow {
        private final String branch;
        private final String tracked;
        private final String lowStock;
        private final String outOfStock;
        private final String inventoryValue;
        private final String status;

        public InventoryRow(String branch, String tracked, String lowStock, String outOfStock, String inventoryValue, String status) {
            this.branch = branch;
            this.tracked = tracked;
            this.lowStock = lowStock;
            this.outOfStock = outOfStock;
            this.inventoryValue = inventoryValue;
            this.status = status;
        }

        public String getBranch() {
            return branch;
        }

        public String getTracked() {
            return tracked;
        }

        public String getLowStock() {
            return lowStock;
        }

        public String getOutOfStock() {
            return outOfStock;
        }

        public String getInventoryValue() {
            return inventoryValue;
        }

        public String getStatus() {
            return status;
        }
    }
}
