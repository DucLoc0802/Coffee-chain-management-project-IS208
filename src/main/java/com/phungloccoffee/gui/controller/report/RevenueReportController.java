package com.phungloccoffee.gui.controller.report;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class RevenueReportController {
    @FXML private ComboBox<String> branchFilterComboBox;
    @FXML private DatePicker fromDatePicker;
    @FXML private DatePicker toDatePicker;
    @FXML private LineChart<String, Number> revenueTrendChart;
    @FXML private BarChart<String, Number> branchRevenueChart;
    @FXML private TableView<RevenueRow> reportTable;
    @FXML private TableColumn<RevenueRow, String> branchColumn;
    @FXML private TableColumn<RevenueRow, String> revenueColumn;
    @FXML private TableColumn<RevenueRow, String> orderCountColumn;
    @FXML private TableColumn<RevenueRow, String> averageColumn;
    @FXML private TableColumn<RevenueRow, String> growthColumn;
    @FXML private TableColumn<RevenueRow, String> statusColumn;

    @FXML
    private void initialize() {
        branchFilterComboBox.getItems().setAll("Tất cả chi nhánh", "CN001", "CN002", "CN003", "CN004", "CN005", "CN006", "CN007", "CN008");
        branchFilterComboBox.getSelectionModel().selectFirst();
        fromDatePicker.setValue(LocalDate.of(2026, 7, 1));
        toDatePicker.setValue(LocalDate.of(2026, 7, 31));

        branchColumn.setCellValueFactory(new PropertyValueFactory<>("branch"));
        revenueColumn.setCellValueFactory(new PropertyValueFactory<>("revenue"));
        orderCountColumn.setCellValueFactory(new PropertyValueFactory<>("orders"));
        averageColumn.setCellValueFactory(new PropertyValueFactory<>("average"));
        growthColumn.setCellValueFactory(new PropertyValueFactory<>("growth"));
        growthColumn.setCellFactory(column -> new GrowthCell<>());
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusColumn.setCellFactory(column -> new StatusCell<>());
        reportTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        loadReport();
    }

    @FXML
    private void loadReport() {
        seedTrendChart();
        seedBranchChart();
        reportTable.setItems(FXCollections.observableArrayList(
                new RevenueRow("CN001 - Chi nhánh trung tâm", "486M", "3.110", "156K", "+16%", "Tốt"),
                new RevenueRow("CN002 - Chi nhánh Phú Nhuận", "420M", "2.760", "152K", "+14%", "Tốt"),
                new RevenueRow("CN003 - Chi nhánh Bình Thạnh", "392M", "2.520", "156K", "+9%", "Tốt"),
                new RevenueRow("CN004 - Chi nhánh Gò Vấp", "350M", "2.260", "155K", "+7%", "Ổn định"),
                new RevenueRow("CN005 - Chi nhánh Thủ Đức", "316M", "2.080", "152K", "+3%", "Ổn định"),
                new RevenueRow("CN006 - Chi nhánh Tân Bình", "288M", "1.940", "148K", "-2%", "Cần kiểm tra"),
                new RevenueRow("CN007 - Chi nhánh Quận 7", "264M", "1.770", "149K", "+4%", "Ổn định"),
                new RevenueRow("CN008 - Chi nhánh Quận 10", "234M", "1.980", "118K", "-5%", "Cảnh báo")
        ));
    }

    private void seedTrendChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().addAll(
                new XYChart.Data<>("Tuần 1", 520),
                new XYChart.Data<>("Tuần 2", 610),
                new XYChart.Data<>("Tuần 3", 760),
                new XYChart.Data<>("Tuần 4", 910)
        );
        revenueTrendChart.getData().setAll(series);
    }

    private void seedBranchChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().addAll(
                new XYChart.Data<>("CN001", 486),
                new XYChart.Data<>("CN002", 420),
                new XYChart.Data<>("CN003", 392),
                new XYChart.Data<>("CN004", 350),
                new XYChart.Data<>("CN005", 316),
                new XYChart.Data<>("CN006", 288),
                new XYChart.Data<>("CN007", 264),
                new XYChart.Data<>("CN008", 234)
        );
        branchRevenueChart.getData().setAll(series);
    }

    private static class GrowthCell<T> extends TableCell<T, String> {
        @Override
        protected void updateItem(String growth, boolean empty) {
            super.updateItem(growth, empty);
            if (empty || growth == null) {
                setGraphic(null);
                setText(null);
                return;
            }
            Label badge = new Label(growth);
            badge.getStyleClass().addAll("status-badge", growth.startsWith("-") ? "status-danger" : "status-success");
            setGraphic(badge);
            setText(null);
            setAlignment(Pos.CENTER_LEFT);
        }
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
                case "Tốt" -> "status-success";
                case "Cần kiểm tra", "Ổn định" -> "status-warning";
                default -> "status-danger";
            });
            setGraphic(badge);
            setText(null);
            setAlignment(Pos.CENTER_LEFT);
        }
    }

    public static class RevenueRow {
        private final String branch;
        private final String revenue;
        private final String orders;
        private final String average;
        private final String growth;
        private final String status;

        public RevenueRow(String branch, String revenue, String orders, String average, String growth, String status) {
            this.branch = branch;
            this.revenue = revenue;
            this.orders = orders;
            this.average = average;
            this.growth = growth;
            this.status = status;
        }

        public String getBranch() {
            return branch;
        }

        public String getRevenue() {
            return revenue;
        }

        public String getOrders() {
            return orders;
        }

        public String getAverage() {
            return average;
        }

        public String getGrowth() {
            return growth;
        }

        public String getStatus() {
            return status;
        }
    }
}
