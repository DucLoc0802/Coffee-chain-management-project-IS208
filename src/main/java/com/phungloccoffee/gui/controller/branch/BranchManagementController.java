package com.phungloccoffee.gui.controller.branch;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class BranchManagementController {
    @FXML private TextField searchField;
    @FXML private ComboBox<String> areaFilterComboBox;
    @FXML private ComboBox<String> statusFilterComboBox;
    @FXML private TableView<BranchRow> branchTable;
    @FXML private TableColumn<BranchRow, String> branchCodeColumn;
    @FXML private TableColumn<BranchRow, String> branchNameColumn;
    @FXML private TableColumn<BranchRow, String> areaColumn;
    @FXML private TableColumn<BranchRow, String> managerColumn;
    @FXML private TableColumn<BranchRow, String> phoneColumn;
    @FXML private TableColumn<BranchRow, String> statusColumn;
    @FXML private TableColumn<BranchRow, Void> actionColumn;

    @FXML
    private void initialize() {
        areaFilterComboBox.getItems().setAll("Tất cả khu vực", "Quận 1", "Phú Nhuận", "Bình Thạnh",
                "Gò Vấp", "Thủ Đức", "Tân Bình", "Quận 7", "Quận 10");
        statusFilterComboBox.getItems().setAll("Tất cả trạng thái", "Đang hoạt động", "Tạm dừng");
        areaFilterComboBox.getSelectionModel().selectFirst();
        statusFilterComboBox.getSelectionModel().selectFirst();

        branchCodeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        branchNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        areaColumn.setCellValueFactory(new PropertyValueFactory<>("area"));
        managerColumn.setCellValueFactory(new PropertyValueFactory<>("manager"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusColumn.setCellFactory(column -> new StatusCell<>());
        actionColumn.setCellFactory(column -> new ActionCell());
        branchTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        loadBranches();
    }

    private void loadBranches() {
        branchTable.setItems(FXCollections.observableArrayList(
                new BranchRow("CN001", "Chi nhánh trung tâm", "Quận 1", "Nguyễn Văn Quản", "0900000001", "Đang hoạt động"),
                new BranchRow("CN002", "Chi nhánh Phú Nhuận", "Phú Nhuận", "Trần Minh Anh", "0900000002", "Đang hoạt động"),
                new BranchRow("CN003", "Chi nhánh Bình Thạnh", "Bình Thạnh", "Lê Hoàng Nam", "0900000003", "Đang hoạt động"),
                new BranchRow("CN004", "Chi nhánh Gò Vấp", "Gò Vấp", "Phạm Thu Hà", "0900000004", "Đang hoạt động"),
                new BranchRow("CN005", "Chi nhánh Thủ Đức", "Thủ Đức", "Võ Thanh Sơn", "0900000005", "Đang hoạt động"),
                new BranchRow("CN006", "Chi nhánh Tân Bình", "Tân Bình", "Đỗ Mỹ Linh", "0900000006", "Đang hoạt động"),
                new BranchRow("CN007", "Chi nhánh Quận 7", "Quận 7", "Nguyễn Hoàng Phúc", "0900000007", "Đang hoạt động"),
                new BranchRow("CN008", "Chi nhánh Quận 10", "Quận 10", "Trống", "0900000008", "Tạm dừng")
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
            badge.getStyleClass().addAll("status-badge", "Đang hoạt động".equals(status) ? "status-success" : "status-warning");
            setGraphic(badge);
            setText(null);
            setAlignment(Pos.CENTER_LEFT);
        }
    }

    private static class ActionCell extends TableCell<BranchRow, Void> {
        private final HBox box = new HBox(8);
        private final Button viewButton = new Button("Xem");
        private final Button editButton = new Button("Sửa");
        private final Button toggleButton = new Button();

        ActionCell() {
            viewButton.getStyleClass().addAll("action-button", "action-view-button");
            editButton.getStyleClass().addAll("action-button", "action-edit-button");
            toggleButton.getStyleClass().addAll("action-button", "action-warning-button");
            box.setAlignment(Pos.CENTER_LEFT);
            box.getChildren().addAll(viewButton, editButton, toggleButton);
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                setGraphic(null);
                return;
            }
            BranchRow row = getTableRow().getItem();
            toggleButton.setText("Tạm dừng".equals(row.getStatus()) ? "Mở lại" : "Tạm dừng");
            toggleButton.getStyleClass().removeAll("action-warning-button", "action-lock-button", "action-approve-button");
            toggleButton.getStyleClass().add("Tạm dừng".equals(row.getStatus()) ? "action-approve-button" : "action-warning-button");
            setGraphic(box);
        }
    }

    public static class BranchRow {
        private final String code;
        private final String name;
        private final String area;
        private final String manager;
        private final String phone;
        private final String status;

        public BranchRow(String code, String name, String area, String manager, String phone, String status) {
            this.code = code;
            this.name = name;
            this.area = area;
            this.manager = manager;
            this.phone = phone;
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public String getArea() {
            return area;
        }

        public String getManager() {
            return manager;
        }

        public String getPhone() {
            return phone;
        }

        public String getStatus() {
            return status;
        }
    }
}
