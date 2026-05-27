package com.phungloccoffee.gui.controller.branch;

import com.phungloccoffee.gui.util.IconFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;

public class BranchDashboardController {
    @FXML private StackPane revenueIcon;
    @FXML private StackPane ordersIcon;
    @FXML private StackPane approvalIcon;
    @FXML private StackPane stockIcon;
    @FXML private LineChart<String, Number> revenueChart;
    @FXML private TableView<ActivityRow> activityTable;
    @FXML private TableColumn<ActivityRow, String> activityColumn;
    @FXML private TableColumn<ActivityRow, String> ownerColumn;
    @FXML private TableColumn<ActivityRow, String> timeColumn;
    @FXML private TableColumn<ActivityRow, String> statusColumn;

    @FXML
    private void initialize() {
        revenueIcon.getChildren().setAll(IconFactory.createReportIcon("money"));
        ordersIcon.getChildren().setAll(IconFactory.createReportIcon("receipt"));
        approvalIcon.getChildren().setAll(IconFactory.createReportIcon("clipboard"));
        stockIcon.getChildren().setAll(IconFactory.createReportIcon("alert"));

        seedChart();
        seedActivityTable();
    }

    private void seedChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("T2", 12.8));
        series.getData().add(new XYChart.Data<>("T3", 14.1));
        series.getData().add(new XYChart.Data<>("T4", 13.6));
        series.getData().add(new XYChart.Data<>("T5", 16.4));
        series.getData().add(new XYChart.Data<>("T6", 18.5));
        series.getData().add(new XYChart.Data<>("T7", 20.2));
        series.getData().add(new XYChart.Data<>("CN", 17.9));
        revenueChart.getData().setAll(series);
    }

    private void seedActivityTable() {
        activityColumn.setCellValueFactory(data -> data.getValue().activityProperty());
        ownerColumn.setCellValueFactory(data -> data.getValue().ownerProperty());
        timeColumn.setCellValueFactory(data -> data.getValue().timeProperty());
        statusColumn.setCellValueFactory(data -> data.getValue().statusProperty());
        statusColumn.setCellFactory(column -> new StatusCell());
        activityTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        activityTable.setItems(FXCollections.observableArrayList(
                row("Duy\u1ec7t phi\u1ebfu xu\u1ea5t PXK-0012", "Qu\u1ea3n l\u00fd chi nh\u00e1nh", "09:15", "Ch\u1edd duy\u1ec7t"),
                row("C\u1eadp nh\u1eadt t\u1ed3n kho c\u1ea3nh b\u00e1o", "Nh\u00e2n vi\u00ean kho", "10:30", "\u0110ang x\u1eed l\u00fd"),
                row("T\u1ed5ng h\u1ee3p doanh thu ca s\u00e1ng", "Thu ng\u00e2n", "11:45", "Ho\u00e0n t\u1ea5t"),
                row("Ki\u1ec3m tra hao h\u1ee5t nguy\u00ean li\u1ec7u", "Nh\u00e2n vi\u00ean kho", "13:20", "Ch\u1edd duy\u1ec7t")
        ));
    }

    private ActivityRow row(String activity, String owner, String time, String status) {
        return new ActivityRow(activity, owner, time, status);
    }

    private static String statusStyle(String status) {
        return switch (status) {
            case "\u0110ang x\u1eed l\u00fd" -> "status-info";
            case "Ho\u00e0n t\u1ea5t" -> "status-success";
            default -> "status-warning";
        };
    }

    private class StatusCell extends TableCell<ActivityRow, String> {
        @Override
        protected void updateItem(String status, boolean empty) {
            super.updateItem(status, empty);
            if (empty || status == null) {
                setGraphic(null);
                setText(null);
                return;
            }
            Label badge = new Label(status);
            badge.getStyleClass().addAll("status-badge", statusStyle(status));
            setGraphic(badge);
            setText(null);
            setAlignment(Pos.CENTER_LEFT);
        }
    }

    public static class ActivityRow {
        private final SimpleStringProperty activity;
        private final SimpleStringProperty owner;
        private final SimpleStringProperty time;
        private final SimpleStringProperty status;

        public ActivityRow(String activity, String owner, String time, String status) {
            this.activity = new SimpleStringProperty(activity);
            this.owner = new SimpleStringProperty(owner);
            this.time = new SimpleStringProperty(time);
            this.status = new SimpleStringProperty(status);
        }

        public SimpleStringProperty activityProperty() { return activity; }
        public SimpleStringProperty ownerProperty() { return owner; }
        public SimpleStringProperty timeProperty() { return time; }
        public SimpleStringProperty statusProperty() { return status; }
    }
}
