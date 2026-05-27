package com.phungloccoffee.gui.controller.account;

import com.phungloccoffee.bus.EmployeeBUS;
import com.phungloccoffee.model.Employee;
import com.phungloccoffee.util.AlertUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class EmployeeListController {
    @FXML private TableView<Employee> employeeTable;
    @FXML private TableColumn<Employee, String> codeColumn;
    @FXML private TableColumn<Employee, String> nameColumn;
    @FXML private TableColumn<Employee, String> phoneColumn;
    @FXML private TableColumn<Employee, String> branchColumn;
    @FXML private TableColumn<Employee, String> positionColumn;
    @FXML private TableColumn<Employee, String> statusColumn;

    private final EmployeeBUS employeeBUS = new EmployeeBUS();

    @FXML
    private void initialize() {
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("employeeCode"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        branchColumn.setCellValueFactory(new PropertyValueFactory<>("branchName"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusColumn.setCellFactory(column -> new StatusCell<>());
        employeeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        loadEmployees();
    }

    @FXML
    private void loadEmployees() {
        try {
            List<Employee> employees = employeeBUS.loadEmployees();
            employeeTable.setItems(FXCollections.observableArrayList(employees.isEmpty() ? sampleEmployees() : employees));
        } catch (Exception e) {
            employeeTable.setItems(FXCollections.observableArrayList(sampleEmployees()));
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
            if (value.contains("\u0111\u00e3 ngh\u1ec9") || value.contains("inactive") || value.equals("0")) {
                return "status-danger";
            }
            if (value.contains("t\u1ea1m") || value.contains("ch\u1edd")) {
                return "status-warning";
            }
            return "status-success";
        }
    }

    private List<Employee> sampleEmployees() {
        return List.of(
                new Employee(1, "NV001", "Quản trị viên", "0900000001", "Hệ thống", "IT Admin", "Đang làm"),
                new Employee(2, "NV002", "Nhân viên thu ngân", "0900000002", "CN001", "Thu ngân", "Đang làm"),
                new Employee(3, "NV003", "Quản lý chi nhánh", "0900000003", "CN001", "Quản lý", "Đang làm"),
                new Employee(4, "NV004", "Nhân viên kho", "0900000004", "CN001", "Kho", "Đang làm"),
                new Employee(5, "NV005", "Ban giám đốc", "0900000005", "Văn phòng", "Giám đốc", "Đang làm")
        );
    }
}

