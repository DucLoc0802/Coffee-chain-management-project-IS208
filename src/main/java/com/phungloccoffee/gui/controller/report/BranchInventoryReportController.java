package com.phungloccoffee.gui.controller.report;

import com.phungloccoffee.gui.util.IconFactory;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class BranchInventoryReportController {
    private static final String ALL_STATUS = "T\u1ea5t c\u1ea3 tr\u1ea1ng th\u00e1i";
    private static final String ALL_CATEGORY = "T\u1ea5t c\u1ea3 nh\u00f3m";
    private static final String STATUS_STABLE = "\u1ed4n \u0111\u1ecbnh";
    private static final String STATUS_LOW = "T\u1ed3n th\u1ea5p";
    private static final String STATUS_REORDER = "C\u1ea7n nh\u1eadp th\u00eam";
    private static final String STATUS_CRITICAL = "C\u1ef1c th\u1ea5p";
    private static final String STATUS_OUT = "H\u1ebft h\u00e0ng";

    @FXML private HBox tabOverview;
    @FXML private HBox tabDetail;
    @FXML private VBox overviewPane;
    @FXML private VBox detailPane;
    @FXML private StackPane overviewTabIcon;
    @FXML private StackPane detailTabIcon;
    @FXML private TextField searchField;
    @FXML private ComboBox<String> statusFilter;
    @FXML private ComboBox<String> categoryFilter;
    @FXML private StackPane productIcon;
    @FXML private StackPane lowStockIcon;
    @FXML private StackPane reorderIcon;
    @FXML private StackPane valueIcon;
    @FXML private BarChart<String, Number> inventoryBarChart;
    @FXML private VBox inventoryAlertList;
    @FXML private TableView<InventoryRow> inventoryDetailTable;
    @FXML private TableColumn<InventoryRow, String> codeColumn;
    @FXML private TableColumn<InventoryRow, String> productColumn;
    @FXML private TableColumn<InventoryRow, String> categoryColumn;
    @FXML private TableColumn<InventoryRow, String> stockColumn;
    @FXML private TableColumn<InventoryRow, String> warningColumn;
    @FXML private TableColumn<InventoryRow, String> ratioColumn;
    @FXML private TableColumn<InventoryRow, String> differenceColumn;
    @FXML private TableColumn<InventoryRow, String> statusColumn;
    @FXML private TableColumn<InventoryRow, String> suggestionColumn;

    private final ObservableList<InventoryRow> rows = FXCollections.observableArrayList(
            row("NL001", "C\u00e0 ph\u00ea rang xay", "C\u00e0 ph\u00ea", "34 kg", "20 kg", "170%", "+14 kg", STATUS_STABLE, "Theo d\u00f5i"),
            row("NL002", "S\u1eefa t\u01b0\u01a1i", "S\u1eefa", "12 l\u00edt", "15 l\u00edt", "80%", "-3 l\u00edt", STATUS_LOW, "Nh\u1eadp th\u00eam"),
            row("NL003", "Ly gi\u1ea5y M", "Bao b\u00ec", "420 c\u00e1i", "200 c\u00e1i", "210%", "+220 c\u00e1i", STATUS_STABLE, "Theo d\u00f5i"),
            row("NL004", "Syrup \u0111\u00e0o", "Syrup", "8 chai", "10 chai", "80%", "-2 chai", STATUS_CRITICAL, "\u0110\u1eb7t h\u00e0ng"),
            row("NL005", "B\u1ed9t matcha", "Kh\u00e1c", "0 kg", "5 kg", "0%", "-5 kg", STATUS_OUT, "Nh\u1eadp kh\u1ea9n")
    );
    private final List<InventoryMetric> metrics = List.of(
            new InventoryMetric("C\u00e0 ph\u00ea", 170, STATUS_STABLE),
            new InventoryMetric("S\u1eefa", 80, STATUS_LOW),
            new InventoryMetric("Bao b\u00ec", 210, STATUS_STABLE),
            new InventoryMetric("Syrup", 80, STATUS_CRITICAL),
            new InventoryMetric("Kh\u00e1c", 0, STATUS_OUT)
    );
    private FilteredList<InventoryRow> filteredRows;

    @FXML
    private void initialize() {
        setupTabs();
        setupFilters();
        setupIcons();
        seedChart();
        renderAlertList();
        seedTable();
    }

    @FXML
    private void showOverviewTab() {
        setActiveTab(true);
    }

    @FXML
    private void showDetailTab() {
        setActiveTab(false);
    }

    @FXML
    private void handleFilter() {
        String keyword = safe(searchField.getText()).toLowerCase();
        String status = statusFilter.getValue();
        String category = categoryFilter.getValue();
        filteredRows.setPredicate(row -> {
            boolean keywordMatch = keyword.isBlank()
                    || row.getCode().toLowerCase().contains(keyword)
                    || row.getProduct().toLowerCase().contains(keyword)
                    || row.getCategory().toLowerCase().contains(keyword);
            boolean statusMatch = status == null
                    || ALL_STATUS.equals(status)
                    || row.getStatus().equals(status)
                    || (STATUS_REORDER.equals(status) && STATUS_CRITICAL.equals(row.getStatus()));
            boolean categoryMatch = category == null || ALL_CATEGORY.equals(category) || row.getCategory().equals(category);
            return keywordMatch && statusMatch && categoryMatch;
        });
    }

    @FXML
    private void handleReset() {
        searchField.clear();
        statusFilter.getSelectionModel().selectFirst();
        categoryFilter.getSelectionModel().selectFirst();
        filteredRows.setPredicate(row -> true);
    }

    private void setupTabs() {
        overviewTabIcon.getChildren().setAll(IconFactory.createReportIcon("trend"));
        detailTabIcon.getChildren().setAll(IconFactory.createReportIcon("list"));
        setActiveTab(true);
    }

    private void setActiveTab(boolean overviewActive) {
        overviewPane.setVisible(overviewActive);
        overviewPane.setManaged(overviewActive);
        detailPane.setVisible(!overviewActive);
        detailPane.setManaged(!overviewActive);
        updateTabStyle(tabOverview, overviewActive);
        updateTabStyle(tabDetail, !overviewActive);
    }

    private void updateTabStyle(HBox tab, boolean active) {
        tab.getStyleClass().remove("inventory-tab-active");
        if (active) {
            tab.getStyleClass().add("inventory-tab-active");
        }
    }

    private void setupFilters() {
        statusFilter.setItems(FXCollections.observableArrayList(ALL_STATUS, STATUS_STABLE, STATUS_LOW, STATUS_REORDER, STATUS_OUT));
        categoryFilter.setItems(FXCollections.observableArrayList(ALL_CATEGORY, "C\u00e0 ph\u00ea", "S\u1eefa", "Bao b\u00ec", "Syrup", "Kh\u00e1c"));
        statusFilter.getSelectionModel().selectFirst();
        categoryFilter.getSelectionModel().selectFirst();
    }

    private void setupIcons() {
        productIcon.getChildren().setAll(IconFactory.createReportIcon("package"));
        lowStockIcon.getChildren().setAll(IconFactory.createReportIcon("alert"));
        reorderIcon.getChildren().setAll(IconFactory.createReportIcon("clipboard"));
        valueIcon.getChildren().setAll(IconFactory.createReportIcon("money"));
    }

    private void seedChart() {
        XYChart.Series<String, Number> currentSeries = new XYChart.Series<>();
        currentSeries.setName("T\u1ed3n hi\u1ec7n t\u1ea1i");
        for (InventoryMetric metric : metrics) {
            currentSeries.getData().add(new XYChart.Data<>(metric.category, metric.inventoryIndex));
        }

        XYChart.Series<String, Number> warningSeries = new XYChart.Series<>();
        warningSeries.setName("M\u1ee9c c\u1ea3nh b\u00e1o");
        for (InventoryMetric metric : metrics) {
            warningSeries.getData().add(new XYChart.Data<>(metric.category, 100));
        }

        inventoryBarChart.getData().setAll(currentSeries, warningSeries);
        inventoryBarChart.setCategoryGap(24);
        inventoryBarChart.setBarGap(4);
        Platform.runLater(() -> applyChartColors(currentSeries, warningSeries));
    }

    private void applyChartColors(XYChart.Series<String, Number> currentSeries, XYChart.Series<String, Number> warningSeries) {
        for (int i = 0; i < currentSeries.getData().size(); i++) {
            Node node = currentSeries.getData().get(i).getNode();
            if (node != null) {
                node.setStyle("-fx-bar-fill: " + colorForStatus(metrics.get(i).status) + ";");
            }
        }
        for (XYChart.Data<String, Number> data : warningSeries.getData()) {
            if (data.getNode() != null) {
                data.getNode().setStyle("-fx-bar-fill: #9ca3af;");
            }
        }
    }

    private void renderAlertList() {
        List<Node> alerts = rows.stream()
                .filter(row -> !STATUS_STABLE.equals(row.getStatus()))
                .map(row -> (Node) createAlertRow(row.getProduct(), row.getStatus(), "C\u00f2n " + row.getStock() + " / c\u1ea3nh b\u00e1o " + row.getWarning()))
                .toList();
        if (alerts.isEmpty()) {
            Label empty = new Label("Kh\u00f4ng c\u00f3 nguy\u00ean li\u1ec7u c\u1ea7n c\u1ea3nh b\u00e1o.");
            empty.getStyleClass().add("section-subtitle");
            inventoryAlertList.getChildren().setAll(empty);
            return;
        }
        inventoryAlertList.getChildren().setAll(alerts);
    }

    private VBox createAlertRow(String product, String status, String detail) {
        Label productLabel = new Label(product);
        productLabel.getStyleClass().add("report-list-label");

        Label badge = new Label(status);
        badge.getStyleClass().addAll("badge", statusStyle(status));

        Region spacer = new Region();
        HBox header = new HBox(productLabel, spacer, badge);
        header.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        Label detailLabel = new Label(detail);
        detailLabel.getStyleClass().add("section-subtitle");

        VBox row = new VBox(6, header, detailLabel);
        row.getStyleClass().add("alert-item");
        row.setPadding(new Insets(0, 0, 12, 0));
        return row;
    }

    private void seedTable() {
        codeColumn.setCellValueFactory(data -> data.getValue().codeProperty());
        productColumn.setCellValueFactory(data -> data.getValue().productProperty());
        categoryColumn.setCellValueFactory(data -> data.getValue().categoryProperty());
        stockColumn.setCellValueFactory(data -> data.getValue().stockProperty());
        warningColumn.setCellValueFactory(data -> data.getValue().warningProperty());
        ratioColumn.setCellValueFactory(data -> data.getValue().ratioProperty());
        differenceColumn.setCellValueFactory(data -> data.getValue().differenceProperty());
        statusColumn.setCellValueFactory(data -> data.getValue().statusProperty());
        suggestionColumn.setCellValueFactory(data -> data.getValue().suggestionProperty());

        codeColumn.setCellFactory(column -> new BlueTextCell());
        ratioColumn.setCellFactory(column -> new RatioCell());
        differenceColumn.setCellFactory(column -> new DifferenceCell());
        statusColumn.setCellFactory(column -> new StatusCell());
        suggestionColumn.setCellFactory(column -> new SuggestionCell());

        filteredRows = new FilteredList<>(rows, row -> true);
        inventoryDetailTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        inventoryDetailTable.setItems(filteredRows);
    }

    private InventoryRow row(String code, String product, String category, String stock, String warning,
                             String ratio, String difference, String status, String suggestion) {
        return new InventoryRow(code, product, category, stock, warning, ratio, difference, status, suggestion);
    }

    private String safe(String value) {
        return value == null ? "" : value.trim();
    }

    private static String statusStyle(String value) {
        return switch (value) {
            case STATUS_LOW -> "status-warning";
            case STATUS_CRITICAL, STATUS_OUT -> "status-danger";
            default -> "status-success";
        };
    }

    private static String suggestionStyle(String value) {
        return switch (value) {
            case "Nh\u1eadp th\u00eam" -> "status-warning";
            case "\u0110\u1eb7t h\u00e0ng", "Nh\u1eadp kh\u1ea9n" -> "status-danger";
            default -> "badge-neutral";
        };
    }

    private static String colorForStatus(String status) {
        return switch (status) {
            case STATUS_LOW -> "#f59e0b";
            case STATUS_CRITICAL, STATUS_OUT -> "#ef4444";
            default -> "#16a34a";
        };
    }

    private static class BlueTextCell extends TableCell<InventoryRow, String> {
        @Override
        protected void updateItem(String value, boolean empty) {
            super.updateItem(value, empty);
            getStyleClass().remove("cell-code");
            if (empty || value == null) {
                setText(null);
                return;
            }
            setText(value);
            getStyleClass().add("cell-code");
            setAlignment(Pos.CENTER_LEFT);
        }
    }

    private static class RatioCell extends TableCell<InventoryRow, String> {
        @Override
        protected void updateItem(String value, boolean empty) {
            super.updateItem(value, empty);
            if (empty || value == null) {
                setGraphic(null);
                setText(null);
                return;
            }
            Label label = new Label(value);
            label.getStyleClass().addAll("badge", value.startsWith("0") ? "status-danger" : "badge-neutral");
            setGraphic(label);
            setText(null);
            setAlignment(Pos.CENTER);
        }
    }

    private static class DifferenceCell extends TableCell<InventoryRow, String> {
        @Override
        protected void updateItem(String value, boolean empty) {
            super.updateItem(value, empty);
            if (empty || value == null) {
                setGraphic(null);
                setText(null);
                return;
            }
            Label label = new Label(value);
            label.getStyleClass().addAll("badge", value.startsWith("-") ? "status-danger" : "status-success");
            setGraphic(label);
            setText(null);
            setAlignment(Pos.CENTER);
        }
    }

    private static class StatusCell extends TableCell<InventoryRow, String> {
        @Override
        protected void updateItem(String status, boolean empty) {
            super.updateItem(status, empty);
            if (empty || status == null) {
                setGraphic(null);
                setText(null);
                return;
            }
            Label label = new Label(status);
            label.getStyleClass().addAll("badge", statusStyle(status));
            setGraphic(label);
            setText(null);
            setAlignment(Pos.CENTER);
        }
    }

    private static class SuggestionCell extends TableCell<InventoryRow, String> {
        @Override
        protected void updateItem(String value, boolean empty) {
            super.updateItem(value, empty);
            if (empty || value == null) {
                setGraphic(null);
                setText(null);
                return;
            }
            Label label = new Label(value);
            label.getStyleClass().addAll("badge", suggestionStyle(value));
            setGraphic(label);
            setText(null);
            setAlignment(Pos.CENTER);
        }
    }

    public static class InventoryRow {
        private final SimpleStringProperty code;
        private final SimpleStringProperty product;
        private final SimpleStringProperty category;
        private final SimpleStringProperty stock;
        private final SimpleStringProperty warning;
        private final SimpleStringProperty ratio;
        private final SimpleStringProperty difference;
        private final SimpleStringProperty status;
        private final SimpleStringProperty suggestion;

        public InventoryRow(String code, String product, String category, String stock, String warning,
                            String ratio, String difference, String status, String suggestion) {
            this.code = new SimpleStringProperty(code);
            this.product = new SimpleStringProperty(product);
            this.category = new SimpleStringProperty(category);
            this.stock = new SimpleStringProperty(stock);
            this.warning = new SimpleStringProperty(warning);
            this.ratio = new SimpleStringProperty(ratio);
            this.difference = new SimpleStringProperty(difference);
            this.status = new SimpleStringProperty(status);
            this.suggestion = new SimpleStringProperty(suggestion);
        }

        public SimpleStringProperty codeProperty() { return code; }
        public SimpleStringProperty productProperty() { return product; }
        public SimpleStringProperty categoryProperty() { return category; }
        public SimpleStringProperty stockProperty() { return stock; }
        public SimpleStringProperty warningProperty() { return warning; }
        public SimpleStringProperty ratioProperty() { return ratio; }
        public SimpleStringProperty differenceProperty() { return difference; }
        public SimpleStringProperty statusProperty() { return status; }
        public SimpleStringProperty suggestionProperty() { return suggestion; }
        public String getCode() { return code.get(); }
        public String getProduct() { return product.get(); }
        public String getCategory() { return category.get(); }
        public String getStock() { return stock.get(); }
        public String getWarning() { return warning.get(); }
        public String getStatus() { return status.get(); }
    }

    private static class InventoryMetric {
        private final String category;
        private final double inventoryIndex;
        private final String status;

        private InventoryMetric(String category, double inventoryIndex, String status) {
            this.category = category;
            this.inventoryIndex = inventoryIndex;
            this.status = status;
        }
    }
}
