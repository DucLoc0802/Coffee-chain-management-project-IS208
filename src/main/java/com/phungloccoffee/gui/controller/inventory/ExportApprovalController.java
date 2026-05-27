package com.phungloccoffee.gui.controller.inventory;

import com.phungloccoffee.gui.util.IconFactory;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ExportApprovalController {
    private static final String STATUS_PENDING = "Ch\u1edd duy\u1ec7t";
    private static final String STATUS_APPROVED = "\u0110\u00e3 duy\u1ec7t";
    private static final String STATUS_REJECTED = "T\u1eeb ch\u1ed1i";

    @FXML private TableView<ExportRequestRow> exportTable;
    @FXML private TableColumn<ExportRequestRow, String> codeColumn;
    @FXML private TableColumn<ExportRequestRow, String> employeeColumn;
    @FXML private TableColumn<ExportRequestRow, String> timeColumn;
    @FXML private TableColumn<ExportRequestRow, String> reasonColumn;
    @FXML private TableColumn<ExportRequestRow, Number> itemCountColumn;
    @FXML private TableColumn<ExportRequestRow, String> totalColumn;
    @FXML private TableColumn<ExportRequestRow, String> statusColumn;
    @FXML private TableColumn<ExportRequestRow, Void> actionColumn;
    @FXML private StackPane approvalTabIconContainer;
    @FXML private StackPane detailTabIconContainer;

    private final ObservableList<ExportRequestRow> rows = FXCollections.observableArrayList(
            new ExportRequestRow("PXK-0012", "NV_001", "19/07/2026", "H\u00e0ng c\u1eadn date", 3, "1.000.000 VN\u0110", STATUS_PENDING),
            new ExportRequestRow("PXK-0013", "NV_002", "19/06/2026", "H\u01b0 h\u1ecfng", 10, "20.000.000 VN\u0110", STATUS_APPROVED),
            new ExportRequestRow("PXK-0014", "NV_001", "15/06/2026", "Xu\u1ea5t ki\u1ec3m tra ch\u1ea5t l\u01b0\u1ee3ng", 5, "3.500.000 VN\u0110", STATUS_PENDING)
    );

    @FXML
    private void initialize() {
        approvalTabIconContainer.getChildren().setAll(IconFactory.createPageTabIcon("back"));
        detailTabIconContainer.getChildren().setAll(IconFactory.createPageTabIcon("clipboard"));

        codeColumn.setCellValueFactory(data -> data.getValue().codeProperty());
        employeeColumn.setCellValueFactory(data -> data.getValue().employeeIdProperty());
        timeColumn.setCellValueFactory(data -> data.getValue().timeProperty());
        reasonColumn.setCellValueFactory(data -> data.getValue().reasonProperty());
        itemCountColumn.setCellValueFactory(data -> data.getValue().itemCountProperty());
        totalColumn.setCellValueFactory(data -> data.getValue().totalProperty());
        statusColumn.setCellValueFactory(data -> data.getValue().statusProperty());

        codeColumn.setCellFactory(column -> new CodeCell());
        employeeColumn.setCellFactory(column -> new EmployeeCell());
        reasonColumn.setCellFactory(column -> new WrapTextCell<>());
        itemCountColumn.setCellFactory(column -> new QuantityCell());
        totalColumn.setCellFactory(column -> new PriceCell());
        statusColumn.setCellFactory(column -> new StatusBadgeCell());
        actionColumn.setCellFactory(column -> new ActionCell());

        exportTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        exportTable.setItems(rows);
    }

    private void approve(ExportRequestRow row) {
        row.setStatus(STATUS_APPROVED);
        exportTable.refresh();
    }

    private void reject(ExportRequestRow row) {
        row.setStatus(STATUS_REJECTED);
        exportTable.refresh();
    }

    private void showDetails(ExportRequestRow row) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Chi ti\u1ebft phi\u1ebfu xu\u1ea5t");
        alert.setHeaderText(row.getCode() + " - " + row.getStatus());
        alert.setContentText("Ng\u01b0\u1eddi l\u1eadp: " + row.getEmployeeId()
                + "\nTh\u1eddi gian: " + row.getTime()
                + "\nL\u00fd do: " + row.getReason()
                + "\nS\u1ed1 l\u01b0\u1ee3ng m\u1eb7t h\u00e0ng: " + row.getItemCount()
                + "\nT\u1ed5ng ti\u1ec1n: " + row.getTotal());
        alert.showAndWait();
    }

    private static String statusStyle(String status) {
        return switch (status) {
            case STATUS_APPROVED -> "badge-approved";
            case STATUS_REJECTED -> "badge-rejected";
            default -> "badge-pending";
        };
    }

    private static String splitCode(String code) {
        int dashIndex = code.indexOf('-');
        if (dashIndex < 0 || dashIndex == code.length() - 1) {
            return code;
        }
        return code.substring(0, dashIndex + 1) + "\n" + code.substring(dashIndex + 1);
    }

    private static String splitCurrency(String value) {
        return value.replace(" VN\u0110", "\nVN\u0110");
    }

    private class CodeCell extends TableCell<ExportRequestRow, String> {
        @Override
        protected void updateItem(String code, boolean empty) {
            super.updateItem(code, empty);
            if (empty || code == null) {
                setGraphic(null);
                setText(null);
                return;
            }
            Label label = new Label(splitCode(code));
            label.getStyleClass().add("cell-code");
            label.setAlignment(Pos.CENTER);
            setGraphic(label);
            setText(null);
            setAlignment(Pos.CENTER);
        }
    }

    private class EmployeeCell extends TableCell<ExportRequestRow, String> {
        @Override
        protected void updateItem(String employeeId, boolean empty) {
            super.updateItem(employeeId, empty);
            if (empty || employeeId == null) {
                setGraphic(null);
                setText(null);
                return;
            }
            Label label = new Label(employeeId);
            label.getStyleClass().add("cell-nv");
            setGraphic(label);
            setText(null);
            setAlignment(Pos.CENTER);
        }
    }

    private class WrapTextCell<T> extends TableCell<ExportRequestRow, T> {
        @Override
        protected void updateItem(T value, boolean empty) {
            super.updateItem(value, empty);
            if (empty || value == null) {
                setGraphic(null);
                setText(null);
                return;
            }
            Label label = new Label(String.valueOf(value));
            label.setWrapText(true);
            label.setMaxWidth(210);
            label.setAlignment(Pos.CENTER);
            setGraphic(label);
            setText(null);
            setAlignment(Pos.CENTER);
        }
    }

    private class QuantityCell extends TableCell<ExportRequestRow, Number> {
        @Override
        protected void updateItem(Number count, boolean empty) {
            super.updateItem(count, empty);
            if (empty || count == null) {
                setGraphic(null);
                setText(null);
                return;
            }
            Label label = new Label(String.valueOf(count.intValue()));
            label.getStyleClass().add("cell-qty");
            setGraphic(label);
            setText(null);
            setAlignment(Pos.CENTER);
        }
    }

    private class PriceCell extends TableCell<ExportRequestRow, String> {
        @Override
        protected void updateItem(String value, boolean empty) {
            super.updateItem(value, empty);
            if (empty || value == null) {
                setGraphic(null);
                setText(null);
                return;
            }
            Label label = new Label(splitCurrency(value));
            label.getStyleClass().add("cell-price");
            label.setAlignment(Pos.CENTER);
            setGraphic(label);
            setText(null);
            setAlignment(Pos.CENTER);
        }
    }

    private class StatusBadgeCell extends TableCell<ExportRequestRow, String> {
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

    private class ActionCell extends TableCell<ExportRequestRow, Void> {
        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                setGraphic(null);
                setText(null);
                return;
            }
            ExportRequestRow row = getTableRow().getItem();
            VBox actionStack = new VBox(6);
            actionStack.setAlignment(Pos.CENTER);

            Button detail = new Button("Xem");
            detail.setOnAction(event -> showDetails(row));

            if (STATUS_PENDING.equals(row.getStatus())) {
                detail.getStyleClass().add("btn-text-blue");
                Button reject = new Button("T\u1eeb ch\u1ed1i");
                reject.getStyleClass().add("btn-reject");
                reject.setOnAction(event -> reject(row));

                Button approve = new Button("Duy\u1ec7t");
                approve.getStyleClass().add("btn-approve");
                approve.setOnAction(event -> approve(row));

                HBox actionRow = new HBox(6, reject, approve);
                actionRow.setAlignment(Pos.CENTER);
                actionStack.getChildren().addAll(detail, actionRow);
            } else {
                detail.getStyleClass().add("btn-view-bg");
                actionStack.getChildren().add(detail);
            }

            setGraphic(actionStack);
            setText(null);
            setAlignment(Pos.CENTER);
        }
    }

    public static class ExportRequestRow {
        private final SimpleStringProperty code;
        private final SimpleStringProperty employeeId;
        private final SimpleStringProperty time;
        private final SimpleStringProperty reason;
        private final SimpleIntegerProperty itemCount;
        private final SimpleStringProperty total;
        private final SimpleStringProperty status;

        public ExportRequestRow(String code, String employeeId, String time, String reason,
                                int itemCount, String total, String status) {
            this.code = new SimpleStringProperty(code);
            this.employeeId = new SimpleStringProperty(employeeId);
            this.time = new SimpleStringProperty(time);
            this.reason = new SimpleStringProperty(reason);
            this.itemCount = new SimpleIntegerProperty(itemCount);
            this.total = new SimpleStringProperty(total);
            this.status = new SimpleStringProperty(status);
        }

        public SimpleStringProperty codeProperty() { return code; }
        public SimpleStringProperty employeeIdProperty() { return employeeId; }
        public SimpleStringProperty timeProperty() { return time; }
        public SimpleStringProperty reasonProperty() { return reason; }
        public SimpleIntegerProperty itemCountProperty() { return itemCount; }
        public SimpleStringProperty totalProperty() { return total; }
        public SimpleStringProperty statusProperty() { return status; }
        public String getCode() { return code.get(); }
        public String getEmployeeId() { return employeeId.get(); }
        public String getTime() { return time.get(); }
        public String getReason() { return reason.get(); }
        public int getItemCount() { return itemCount.get(); }
        public String getTotal() { return total.get(); }
        public String getStatus() { return status.get(); }
        public void setStatus(String status) { this.status.set(status); }
    }
}
