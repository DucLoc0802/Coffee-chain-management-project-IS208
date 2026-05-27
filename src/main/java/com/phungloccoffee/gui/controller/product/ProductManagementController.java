package com.phungloccoffee.gui.controller.product;

import com.phungloccoffee.bus.ProductBUS;
import com.phungloccoffee.model.Product;
import com.phungloccoffee.util.AlertUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.util.List;

public class ProductManagementController {
    @FXML private TableView<Product> productTable;
    @FXML private TableColumn<Product, String> codeColumn;
    @FXML private TableColumn<Product, String> nameColumn;
    @FXML private TableColumn<Product, BigDecimal> priceColumn;
    @FXML private TableColumn<Product, String> statusColumn;
    @FXML private TextField codeField;
    @FXML private TextField nameField;
    @FXML private TextField priceField;

    private final ProductBUS productBUS = new ProductBUS();

    @FXML
    private void initialize() {
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusColumn.setCellFactory(column -> new StatusCell<>());
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        loadProducts();
    }

    @FXML
    private void loadProducts() {
        try {
            List<Product> products = productBUS.loadProducts();
            productTable.setItems(FXCollections.observableArrayList(products.isEmpty() ? sampleProducts() : products));
        } catch (Exception e) {
            productTable.setItems(FXCollections.observableArrayList(sampleProducts()));
        }
    }

    @FXML
    private void saveProduct() {
        try {
            Product product = new Product(codeField.getText(), "DM001", nameField.getText(), "THANH_PHAM", "ML",
                    new BigDecimal(priceField.getText()), BigDecimal.ZERO, 1, null, null);
            productBUS.saveProduct(product);
            loadProducts();
            AlertUtils.showInfo("Đã lưu sản phẩm.");
        } catch (Exception e) {
            AlertUtils.showError(e.getMessage());
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
            badge.getStyleClass().addAll("status-badge", styleFor(status));
            setGraphic(badge);
            setText(null);
            setAlignment(Pos.CENTER_LEFT);
        }

        private String styleFor(String status) {
            String value = status.toLowerCase();
            if (value.contains("ng\u1eebng") || value.contains("inactive") || value.equals("0")) {
                return "status-danger";
            }
            if (value.contains("nh\u00e1p") || value.contains("ch\u1edd")) {
                return "status-warning";
            }
            return "status-success";
        }
    }

    private List<Product> sampleProducts() {
        return List.of(
                new Product("SP001", "DM001", "Cà phê sữa", "THANH_PHAM", "ly", new BigDecimal("50000"), BigDecimal.ZERO, 1, null, null),
                new Product("SP002", "DM001", "Bạc xỉu", "THANH_PHAM", "ly", new BigDecimal("50000"), BigDecimal.ZERO, 1, null, null),
                new Product("SP003", "DM001", "Latte đá", "THANH_PHAM", "ly", new BigDecimal("65000"), BigDecimal.ZERO, 1, null, null),
                new Product("SP004", "DM002", "Trà đào cam sả", "THANH_PHAM", "ly", new BigDecimal("60000"), BigDecimal.ZERO, 1, null, null),
                new Product("SP005", "DM003", "Croissant bơ", "THANH_PHAM", "cái", new BigDecimal("50000"), BigDecimal.ZERO, 1, null, null),
                new Product("SP006", "DM002", "Matcha latte", "THANH_PHAM", "ly", new BigDecimal("65000"), BigDecimal.ZERO, 0, null, null)
        );
    }
}
