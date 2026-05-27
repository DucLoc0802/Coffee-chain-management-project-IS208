package com.phungloccoffee.gui.controller.report;

import com.phungloccoffee.gui.util.IconFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;

import java.time.LocalDate;
import java.util.List;

public class BranchRevenueReportController {
    @FXML private Button dayTab;
    @FXML private Button monthTab;
    @FXML private Button quarterTab;
    @FXML private Button yearTab;
    @FXML private ComboBox<String> branchCombo;
    @FXML private DatePicker fromDatePicker;
    @FXML private DatePicker toDatePicker;
    @FXML private StackPane revenueIcon;
    @FXML private StackPane orderIcon;
    @FXML private StackPane avgIcon;
    @FXML private StackPane growthIcon;
    @FXML private LineChart<String, Number> revenueLineChart;
    @FXML private TableView<RevenueRow> revenueTable;
    @FXML private TableColumn<RevenueRow, String> dateColumn;
    @FXML private TableColumn<RevenueRow, String> revenueColumn;
    @FXML private TableColumn<RevenueRow, String> orderColumn;
    @FXML private TableColumn<RevenueRow, String> avgColumn;
    @FXML private TableColumn<RevenueRow, String> growthColumn;
    @FXML private TableColumn<RevenueRow, String> noteColumn;

    @FXML
    private void initialize() {
        branchCombo.setItems(FXCollections.observableArrayList("Chi nh\u00e1nh trung t\u00e2m", "Chi nh\u00e1nh CN01", "Chi nh\u00e1nh CN02"));
        branchCombo.getSelectionModel().selectFirst();
        fromDatePicker.setValue(LocalDate.of(2026, 7, 1));
        toDatePicker.setValue(LocalDate.of(2026, 7, 31));

        revenueIcon.getChildren().setAll(IconFactory.createReportIcon("money"));
        orderIcon.getChildren().setAll(IconFactory.createReportIcon("receipt"));
        avgIcon.getChildren().setAll(IconFactory.createReportIcon("cart"));
        growthIcon.getChildren().setAll(IconFactory.createReportIcon("trend"));

        seedChart();
        seedTable();
    }

    @FXML private void handleDayPeriod() { setActivePeriod(dayTab); }
    @FXML private void handleMonthPeriod() { setActivePeriod(monthTab); }
    @FXML private void handleQuarterPeriod() { setActivePeriod(quarterTab); }
    @FXML private void handleYearPeriod() { setActivePeriod(yearTab); }

    private void setActivePeriod(Button active) {
        List.of(dayTab, monthTab, quarterTab, yearTab)
                .forEach(button -> button.getStyleClass().remove("period-tab-active"));
        if (!active.getStyleClass().contains("period-tab-active")) {
            active.getStyleClass().add("period-tab-active");
        }
    }

    private void seedChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Tu\u1ea7n 1", 92));
        series.getData().add(new XYChart.Data<>("Tu\u1ea7n 2", 108));
        series.getData().add(new XYChart.Data<>("Tu\u1ea7n 3", 134));
        series.getData().add(new XYChart.Data<>("Tu\u1ea7n 4", 152));
        revenueLineChart.getData().setAll(series);
    }

    private void seedTable() {
        dateColumn.setCellValueFactory(data -> data.getValue().dateProperty());
        revenueColumn.setCellValueFactory(data -> data.getValue().revenueProperty());
        orderColumn.setCellValueFactory(data -> data.getValue().ordersProperty());
        avgColumn.setCellValueFactory(data -> data.getValue().averageProperty());
        growthColumn.setCellValueFactory(data -> data.getValue().growthProperty());
        noteColumn.setCellValueFactory(data -> data.getValue().noteProperty());
        growthColumn.setCellFactory(column -> new GrowthCell());
        revenueTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        revenueTable.setItems(FXCollections.observableArrayList(
                row("19/07/2026", "18.500.000 VN\u0110", "126", "147.000 VN\u0110", "+12%", "Cao \u0111i\u1ec3m cu\u1ed1i tu\u1ea7n"),
                row("18/07/2026", "16.200.000 VN\u0110", "108", "150.000 VN\u0110", "+8%", "\u1ed4n \u0111\u1ecbnh"),
                row("17/07/2026", "14.900.000 VN\u0110", "96", "155.000 VN\u0110", "-2%", "Ng\u00e0y th\u01b0\u1eddng"),
                row("16/07/2026", "15.400.000 VN\u0110", "102", "151.000 VN\u0110", "0%", "Theo k\u1ebf ho\u1ea1ch")
        ));
    }

    private RevenueRow row(String date, String revenue, String orders, String average, String growth, String note) {
        return new RevenueRow(date, revenue, orders, average, growth, note);
    }

    private class GrowthCell extends TableCell<RevenueRow, String> {
        @Override
        protected void updateItem(String growth, boolean empty) {
            super.updateItem(growth, empty);
            if (empty || growth == null) {
                setGraphic(null);
                setText(null);
                return;
            }
            Label badge = new Label(growth);
            String style = growth.startsWith("-") ? "status-danger" : growth.startsWith("+") ? "status-success" : "badge-neutral";
            badge.getStyleClass().addAll("badge", style);
            setGraphic(badge);
            setText(null);
            setAlignment(Pos.CENTER);
        }
    }

    public static class RevenueRow {
        private final SimpleStringProperty date;
        private final SimpleStringProperty revenue;
        private final SimpleStringProperty orders;
        private final SimpleStringProperty average;
        private final SimpleStringProperty growth;
        private final SimpleStringProperty note;

        public RevenueRow(String date, String revenue, String orders, String average, String growth, String note) {
            this.date = new SimpleStringProperty(date);
            this.revenue = new SimpleStringProperty(revenue);
            this.orders = new SimpleStringProperty(orders);
            this.average = new SimpleStringProperty(average);
            this.growth = new SimpleStringProperty(growth);
            this.note = new SimpleStringProperty(note);
        }

        public SimpleStringProperty dateProperty() { return date; }
        public SimpleStringProperty revenueProperty() { return revenue; }
        public SimpleStringProperty ordersProperty() { return orders; }
        public SimpleStringProperty averageProperty() { return average; }
        public SimpleStringProperty growthProperty() { return growth; }
        public SimpleStringProperty noteProperty() { return note; }
    }
}
