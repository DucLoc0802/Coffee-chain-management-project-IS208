package com.phungloccoffee.gui.controller.account;

import com.phungloccoffee.util.AlertUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PermissionController {
    @FXML private ComboBox<String> roleFilterComboBox;
    @FXML private ComboBox<String> moduleFilterComboBox;
    @FXML private TableView<PermissionRow> permissionTable;
    @FXML private TableColumn<PermissionRow, String> featureColumn;
    @FXML private TableColumn<PermissionRow, String> cashierColumn;
    @FXML private TableColumn<PermissionRow, String> warehouseColumn;
    @FXML private TableColumn<PermissionRow, String> managerColumn;
    @FXML private TableColumn<PermissionRow, String> adminColumn;
    @FXML private TableColumn<PermissionRow, String> directorColumn;

    @FXML
    private void initialize() {
        roleFilterComboBox.getItems().setAll("Tất cả vai trò", "Thu ngân", "Kho", "Quản lý", "Admin", "Ban giám đốc");
        moduleFilterComboBox.getItems().setAll("Tất cả nhóm", "POS", "Kho", "Tài khoản", "Báo cáo", "Sản phẩm");
        roleFilterComboBox.getSelectionModel().selectFirst();
        moduleFilterComboBox.getSelectionModel().selectFirst();

        featureColumn.setCellValueFactory(new PropertyValueFactory<>("feature"));
        cashierColumn.setCellValueFactory(new PropertyValueFactory<>("cashier"));
        warehouseColumn.setCellValueFactory(new PropertyValueFactory<>("warehouse"));
        managerColumn.setCellValueFactory(new PropertyValueFactory<>("manager"));
        adminColumn.setCellValueFactory(new PropertyValueFactory<>("admin"));
        directorColumn.setCellValueFactory(new PropertyValueFactory<>("director"));

        cashierColumn.setCellFactory(column -> new PermissionCell());
        warehouseColumn.setCellFactory(column -> new PermissionCell());
        managerColumn.setCellFactory(column -> new PermissionCell());
        adminColumn.setCellFactory(column -> new PermissionCell());
        directorColumn.setCellFactory(column -> new PermissionCell());
        permissionTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        loadPermissions();
    }

    @FXML
    private void loadPermissions() {
        permissionTable.setItems(FXCollections.observableArrayList(
                new PermissionRow("POS bán hàng", "✓", "—", "—", "—", "—"),
                new PermissionRow("Thanh toán", "✓", "—", "—", "—", "—"),
                new PermissionRow("Lịch sử giao dịch", "✓", "—", "✓", "—", "✓"),
                new PermissionRow("Tồn kho", "—", "✓", "✓", "—", "✓"),
                new PermissionRow("Lập phiếu nhập kho", "—", "✓", "—", "—", "—"),
                new PermissionRow("Lập phiếu xuất kho", "—", "✓", "—", "—", "—"),
                new PermissionRow("Duyệt nhập kho", "—", "—", "✓", "—", "—"),
                new PermissionRow("Duyệt xuất kho", "—", "—", "✓", "—", "—"),
                new PermissionRow("Nhân viên chi nhánh", "—", "—", "✓", "—", "—"),
                new PermissionRow("Quản lý tài khoản", "—", "—", "—", "✓", "—"),
                new PermissionRow("Phân quyền", "—", "—", "—", "✓", "—"),
                new PermissionRow("Báo cáo doanh thu", "—", "—", "✓", "—", "✓"),
                new PermissionRow("Báo cáo tồn kho", "—", "—", "✓", "—", "✓"),
                new PermissionRow("Quản lý sản phẩm", "—", "—", "—", "—", "✓")
        ));
    }

    @FXML
    private void savePermissions() {
        AlertUtils.showInfo("Đã ghi nhận cấu hình phân quyền trên giao diện demo.");
    }

    private static class PermissionCell extends TableCell<PermissionRow, String> {
        @Override
        protected void updateItem(String value, boolean empty) {
            super.updateItem(value, empty);
            if (empty || value == null) {
                setGraphic(null);
                setText(null);
                return;
            }
            Label label = new Label(value);
            label.getStyleClass().add("✓".equals(value) ? "permission-check" : "permission-dash");
            setGraphic(label);
            setText(null);
            setAlignment(Pos.CENTER);
        }
    }

    public static class PermissionRow {
        private final String feature;
        private final String cashier;
        private final String warehouse;
        private final String manager;
        private final String admin;
        private final String director;

        public PermissionRow(String feature, String cashier, String warehouse, String manager, String admin, String director) {
            this.feature = feature;
            this.cashier = cashier;
            this.warehouse = warehouse;
            this.manager = manager;
            this.admin = admin;
            this.director = director;
        }

        public String getFeature() {
            return feature;
        }

        public String getCashier() {
            return cashier;
        }

        public String getWarehouse() {
            return warehouse;
        }

        public String getManager() {
            return manager;
        }

        public String getAdmin() {
            return admin;
        }

        public String getDirector() {
            return director;
        }
    }
}
