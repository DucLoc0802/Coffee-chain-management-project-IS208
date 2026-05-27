package com.phungloccoffee.gui.controller.inventory;

import com.phungloccoffee.bus.InventoryAuditBUS;
import com.phungloccoffee.model.InventoryAudit;
import com.phungloccoffee.util.AlertUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.util.List;

public class InventoryAuditController {
    @FXML private TableView<InventoryAudit> auditTable;
    @FXML private TableColumn<InventoryAudit, String> codeColumn;
    @FXML private TableColumn<InventoryAudit, String> branchColumn;
    @FXML private TableColumn<InventoryAudit, LocalDateTime> auditedAtColumn;
    @FXML private TableColumn<InventoryAudit, String> statusColumn;

    private final InventoryAuditBUS auditBUS = new InventoryAuditBUS();

    @FXML
    private void initialize() {
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("auditCode"));
        branchColumn.setCellValueFactory(new PropertyValueFactory<>("branchName"));
        auditedAtColumn.setCellValueFactory(new PropertyValueFactory<>("auditedAt"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusColumn.setCellFactory(column -> new StatusCell<>());
        auditTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        loadAudits();
    }

    @FXML
    private void loadAudits() {
        try {
            List<InventoryAudit> audits = auditBUS.loadRecentAudits();
            auditTable.setItems(FXCollections.observableArrayList(audits.isEmpty() ? sampleAudits() : audits));
        } catch (Exception e) {
            auditTable.setItems(FXCollections.observableArrayList(sampleAudits()));
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
            if (value.contains("l\u1ed7i") || value.contains("t\u1eeb ch\u1ed1i")) {
                return "status-danger";
            }
            if (value.contains("ch\u1edd") || value.contains("nh\u00e1p")) {
                return "status-warning";
            }
            if (value.contains("\u0111ang")) {
                return "status-info";
            }
            return "status-success";
        }
    }

    private List<InventoryAudit> sampleAudits() {
        LocalDateTime now = LocalDateTime.now();
        return List.of(
                new InventoryAudit(1, "KK001", "CN001 - Chi nhánh trung tâm", 4, now.minusDays(1), "Hoàn tất"),
                new InventoryAudit(2, "KK002", "CN003 - Bình Thạnh", 4, now.minusDays(2), "Đang xử lý"),
                new InventoryAudit(3, "KK003", "CN005 - Thủ Đức", 4, now.minusDays(3), "Chờ duyệt"),
                new InventoryAudit(4, "KK004", "CN008 - Quận 10", 4, now.minusDays(4), "Hoàn tất")
        );
    }
}

