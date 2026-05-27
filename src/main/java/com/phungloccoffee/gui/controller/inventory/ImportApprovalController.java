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

public class ImportApprovalController {
    private static final String STATUS_PENDING = "Ch\u1edd duy\u1ec7t";
    private static final String STATUS_APPROVED = "\u0110\u00e3 duy\u1ec7t";
    private static final String STATUS_REJECTED = "T\u1eeb ch\u1ed1i";

    @FXML private TableView<Row> importTable;
    @FXML private TableColumn<Row, String> codeColumn;
    @FXML private TableColumn<Row, String> creatorColumn;
    @FXML private TableColumn<Row, String> dateColumn;
    @FXML private TableColumn<Row, String> supplierColumn;
    @FXML private TableColumn<Row, Number> itemCountColumn;
    @FXML private TableColumn<Row, String> totalColumn;
    @FXML private TableColumn<Row, String> statusColumn;
    @FXML private TableColumn<Row, Void> actionColumn;
    @FXML private StackPane importTabIconContainer;
    @FXML private StackPane detailTabIconContainer;

    private final ObservableList<Row> rows = FXCollections.observableArrayList(
            new Row("PNK-001", "NV_003", "19/07/2026", "C\u00e0 ph\u00ea T\u00e2y Nguy\u00ean", 6, "8.500.000 VN\u0110", STATUS_PENDING),
            new Row("PNK-002", "NV_004", "18/07/2026", "S\u1eefa \u0110\u00e0 L\u1ea1t", 4, "4.200.000 VN\u0110", STATUS_APPROVED),
            new Row("PNK-003", "NV_003", "17/07/2026", "Bao b\u00ec Vi\u1ec7t", 2, "2.100.000 VN\u0110", STATUS_REJECTED)
    );

    @FXML
    private void initialize() {
        importTabIconContainer.getChildren().setAll(IconFactory.createPageTabIcon("arrow-down"));
        detailTabIconContainer.getChildren().setAll(IconFactory.createPageTabIcon("clipboard"));

        codeColumn.setCellValueFactory(data -> data.getValue().codeProperty());
        creatorColumn.setCellValueFactory(data -> data.getValue().creatorProperty());
        dateColumn.setCellValueFactory(data -> data.getValue().dateProperty());
        supplierColumn.setCellValueFactory(data -> data.getValue().supplierProperty());
        itemCountColumn.setCellValueFactory(data -> data.getValue().itemCountProperty());
        totalColumn.setCellValueFactory(data -> data.getValue().totalProperty());
        statusColumn.setCellValueFactory(data -> data.getValue().statusProperty());

        codeColumn.setCellFactory(column -> new CodeCell());
        creatorColumn.setCellFactory(column -> new CreatorCell());
        supplierColumn.setCellFactory(column -> new WrapTextCell<>());
        itemCountColumn.setCellFactory(column -> new QuantityCell());
        totalColumn.setCellFactory(column -> new PriceCell());
        statusColumn.setCellFactory(column -> new StatusCell());
        actionColumn.setCellFactory(column -> new ActionCell());

        importTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        importTable.setItems(rows);
    }

    private void approve(Row row) {
        row.setStatus(STATUS_APPROVED);
        importTable.refresh();
    }

    private void reject(Row row) {
        row.setStatus(STATUS_REJECTED);
        importTable.refresh();
    }

    private void showDetails(Row row) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Chi ti\u1ebft phi\u1ebfu nh\u1eadp");
        alert.setHeaderText(row.getCode() + " - " + row.getStatus());
        alert.setContentText("Ng\u01b0\u1eddi l\u1eadp: " + row.getCreator()
                + "\nNg\u00e0y l\u1eadp: " + row.getDate()
                + "\nNh\u00e0 cung c\u1ea5p: " + row.getSupplier()
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

    private class CodeCell extends TableCell<Row, String> {
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

    private class CreatorCell extends TableCell<Row, String> {
        @Override
        protected void updateItem(String creator, boolean empty) {
            super.updateItem(creator, empty);
            if (empty || creator == null) {
                setGraphic(null);
                setText(null);
                return;
            }
            Label label = new Label(creator);
            label.getStyleClass().add("cell-nv");
            setGraphic(label);
            setText(null);
            setAlignment(Pos.CENTER);
        }
    }

    private class WrapTextCell<T> extends TableCell<Row, T> {
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
            label.setMaxWidth(240);
            label.setAlignment(Pos.CENTER);
            setGraphic(label);
            setText(null);
            setAlignment(Pos.CENTER);
        }
    }

    private class PriceCell extends TableCell<Row, String> {
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

    private class QuantityCell extends TableCell<Row, Number> {
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

    private class StatusCell extends TableCell<Row, String> {
        @Override
        protected void updateItem(String status, boolean empty) {
            super.updateItem(status, empty);
            if (empty || status == null) {
                setGraphic(null);
                setText(null);
                return;
            }
            Label badge = new Label(status);
            badge.getStyleClass().addAll("badge", statusStyle(status));
            setGraphic(badge);
            setText(null);
            setAlignment(Pos.CENTER);
        }
    }

    private class ActionCell extends TableCell<Row, Void> {
        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                setGraphic(null);
                setText(null);
                return;
            }
            Row row = getTableRow().getItem();
            VBox actionStack = new VBox(6);
            actionStack.setAlignment(Pos.CENTER);

            Button detail = new Button("Xem");
            detail.setOnAction(event -> showDetails(row));

            if (STATUS_PENDING.equals(row.getStatus())) {
                detail.getStyleClass().add("btn-text-blue");
                Button approve = new Button("Duy\u1ec7t");
                approve.getStyleClass().add("btn-approve");
                approve.setOnAction(event -> approve(row));

                Button reject = new Button("T\u1eeb ch\u1ed1i");
                reject.getStyleClass().add("btn-reject");
                reject.setOnAction(event -> reject(row));

                HBox actionRow = new HBox(6, approve, reject);
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

    public static class Row {
        private final SimpleStringProperty code;
        private final SimpleStringProperty creator;
        private final SimpleStringProperty date;
        private final SimpleStringProperty supplier;
        private final SimpleIntegerProperty itemCount;
        private final SimpleStringProperty total;
        private final SimpleStringProperty status;

        public Row(String code, String creator, String date, String supplier, int itemCount, String total, String status) {
            this.code = new SimpleStringProperty(code);
            this.creator = new SimpleStringProperty(creator);
            this.date = new SimpleStringProperty(date);
            this.supplier = new SimpleStringProperty(supplier);
            this.itemCount = new SimpleIntegerProperty(itemCount);
            this.total = new SimpleStringProperty(total);
            this.status = new SimpleStringProperty(status);
        }

        public SimpleStringProperty codeProperty() { return code; }
        public SimpleStringProperty creatorProperty() { return creator; }
        public SimpleStringProperty dateProperty() { return date; }
        public SimpleStringProperty supplierProperty() { return supplier; }
        public SimpleIntegerProperty itemCountProperty() { return itemCount; }
        public SimpleStringProperty totalProperty() { return total; }
        public SimpleStringProperty statusProperty() { return status; }
        public String getCode() { return code.get(); }
        public String getCreator() { return creator.get(); }
        public String getDate() { return date.get(); }
        public String getSupplier() { return supplier.get(); }
        public int getItemCount() { return itemCount.get(); }
        public String getTotal() { return total.get(); }
        public String getStatus() { return status.get(); }
        public void setStatus(String value) { status.set(value); }
    }
}
