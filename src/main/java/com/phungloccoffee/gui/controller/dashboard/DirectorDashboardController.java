package com.phungloccoffee.gui.controller.dashboard;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DirectorDashboardController {
    @FXML private BarChart<String, Number> branchRevenueChart;
    @FXML private TableView<ActivityRow> activityTable;
    @FXML private TableColumn<ActivityRow, String> branchColumn;
    @FXML private TableColumn<ActivityRow, String> metricColumn;
    @FXML private TableColumn<ActivityRow, String> valueColumn;
    @FXML private TableColumn<ActivityRow, String> statusColumn;
    @FXML private TableColumn<ActivityRow, String> noteColumn;

    @FXML
    private void initialize() {
        seedChart();
        branchColumn.setCellValueFactory(new PropertyValueFactory<>("branch"));
        metricColumn.setCellValueFactory(new PropertyValueFactory<>("metric"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusColumn.setCellFactory(column -> new StatusCell<>());
        noteColumn.setCellValueFactory(new PropertyValueFactory<>("note"));
        activityTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        activityTable.setItems(FXCollections.observableArrayList(
                new ActivityRow("CN001", "Doanh thu", "86M", "Tốt", "Cao nhất hôm nay"),
                new ActivityRow("CN002", "Đơn hàng", "412", "Tốt", "Tăng 8%"),
                new ActivityRow("CN005", "Tồn kho", "5 cảnh báo", "Cần xử lý", "Kiểm tra kho"),
                new ActivityRow("CN008", "Vận hành", "Tạm dừng", "Cảnh báo", "Đang bảo trì")
        ));
    }

    private void seedChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Doanh thu");
        series.getData().addAll(
                new XYChart.Data<>("CN001", 86),
                new XYChart.Data<>("CN002", 74),
                new XYChart.Data<>("CN003", 69),
                new XYChart.Data<>("CN004", 58),
                new XYChart.Data<>("CN005", 52),
                new XYChart.Data<>("CN006", 48),
                new XYChart.Data<>("CN007", 44),
                new XYChart.Data<>("CN008", 39)
        );
        branchRevenueChart.getData().setAll(series);
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
                case "Cần xử lý" -> "status-warning";
                default -> "status-danger";
            });
            setGraphic(badge);
            setText(null);
            setAlignment(Pos.CENTER_LEFT);
        }
    }

    public static class ActivityRow {
        private final String branch;
        private final String metric;
        private final String value;
        private final String status;
        private final String note;

        public ActivityRow(String branch, String metric, String value, String status, String note) {
            this.branch = branch;
            this.metric = metric;
            this.value = value;
            this.status = status;
            this.note = note;
        }

        public String getBranch() {
            return branch;
        }

        public String getMetric() {
            return metric;
        }

        public String getValue() {
            return value;
        }

        public String getStatus() {
            return status;
        }

        public String getNote() {
            return note;
        }
    }
}
