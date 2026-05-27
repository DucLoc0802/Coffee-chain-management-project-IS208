package com.phungloccoffee.gui.controller.report;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class BestSellerReportController {
    @FXML private ComboBox<String> periodComboBox;
    @FXML private ComboBox<String> branchComboBox;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private BarChart<String, Number> bestSellerChart;
    @FXML private TableView<BestSellerRow> bestSellerTable;
    @FXML private TableColumn<BestSellerRow, String> rankColumn;
    @FXML private TableColumn<BestSellerRow, String> productColumn;
    @FXML private TableColumn<BestSellerRow, String> categoryColumn;
    @FXML private TableColumn<BestSellerRow, String> quantityColumn;
    @FXML private TableColumn<BestSellerRow, String> revenueColumn;
    @FXML private TableColumn<BestSellerRow, String> shareColumn;
    @FXML private TableColumn<BestSellerRow, String> trendColumn;

    @FXML
    private void initialize() {
        periodComboBox.getItems().setAll("Tháng này", "Quý này", "Năm nay");
        branchComboBox.getItems().setAll("Tất cả chi nhánh", "CN001", "CN002", "CN003", "CN004", "CN005", "CN006", "CN007", "CN008");
        categoryComboBox.getItems().setAll("Tất cả nhóm", "Cà phê", "Trà", "Bánh", "Khác");
        periodComboBox.getSelectionModel().selectFirst();
        branchComboBox.getSelectionModel().selectFirst();
        categoryComboBox.getSelectionModel().selectFirst();

        seedChart();
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        productColumn.setCellValueFactory(new PropertyValueFactory<>("product"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        revenueColumn.setCellValueFactory(new PropertyValueFactory<>("revenue"));
        shareColumn.setCellValueFactory(new PropertyValueFactory<>("share"));
        trendColumn.setCellValueFactory(new PropertyValueFactory<>("trend"));
        trendColumn.setCellFactory(column -> new TrendCell<>());
        bestSellerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        bestSellerTable.setItems(FXCollections.observableArrayList(
                new BestSellerRow("1", "Cà phê sữa", "Cà phê", "2.180", "109M", "17%", "+16%"),
                new BestSellerRow("2", "Bạc xỉu", "Cà phê", "1.840", "92M", "14%", "+12%"),
                new BestSellerRow("3", "Latte đá", "Cà phê", "1.360", "88M", "11%", "+8%"),
                new BestSellerRow("4", "Trà đào cam sả", "Trà", "1.220", "73M", "9%", "+6%"),
                new BestSellerRow("5", "Matcha latte", "Trà", "1.050", "68M", "8%", "+10%"),
                new BestSellerRow("6", "Americano", "Cà phê", "980", "49M", "8%", "+2%"),
                new BestSellerRow("7", "Croissant bơ", "Bánh", "820", "41M", "6%", "+18%"),
                new BestSellerRow("8", "Cold brew", "Cà phê", "760", "53M", "6%", "-3%"),
                new BestSellerRow("9", "Trà sữa Phụng Lộc", "Trà", "690", "45M", "5%", "+4%"),
                new BestSellerRow("10", "Bánh tiramisu", "Bánh", "560", "34M", "4%", "+9%")
        ));
    }

    private void seedChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().addAll(
                new XYChart.Data<>("Cà phê sữa", 2180),
                new XYChart.Data<>("Bạc xỉu", 1840),
                new XYChart.Data<>("Latte", 1360),
                new XYChart.Data<>("Trà đào", 1220),
                new XYChart.Data<>("Matcha", 1050),
                new XYChart.Data<>("Americano", 980),
                new XYChart.Data<>("Croissant", 820),
                new XYChart.Data<>("Cold brew", 760),
                new XYChart.Data<>("Trà sữa", 690),
                new XYChart.Data<>("Tiramisu", 560)
        );
        bestSellerChart.getData().setAll(series);
    }

    private static class TrendCell<T> extends TableCell<T, String> {
        @Override
        protected void updateItem(String trend, boolean empty) {
            super.updateItem(trend, empty);
            if (empty || trend == null) {
                setGraphic(null);
                setText(null);
                return;
            }
            Label badge = new Label(trend);
            badge.getStyleClass().addAll("status-badge", trend.startsWith("-") ? "status-danger" : "status-success");
            setGraphic(badge);
            setText(null);
            setAlignment(Pos.CENTER_LEFT);
        }
    }

    public static class BestSellerRow {
        private final String rank;
        private final String product;
        private final String category;
        private final String quantity;
        private final String revenue;
        private final String share;
        private final String trend;

        public BestSellerRow(String rank, String product, String category, String quantity,
                             String revenue, String share, String trend) {
            this.rank = rank;
            this.product = product;
            this.category = category;
            this.quantity = quantity;
            this.revenue = revenue;
            this.share = share;
            this.trend = trend;
        }

        public String getRank() {
            return rank;
        }

        public String getProduct() {
            return product;
        }

        public String getCategory() {
            return category;
        }

        public String getQuantity() {
            return quantity;
        }

        public String getRevenue() {
            return revenue;
        }

        public String getShare() {
            return share;
        }

        public String getTrend() {
            return trend;
        }
    }
}
