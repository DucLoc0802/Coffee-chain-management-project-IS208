package com.phungloccoffee.gui.controller.inventory;

import com.phungloccoffee.gui.util.IconFactory;
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

public class MaterialLossApprovalController {
    private static final String STATUS_PENDING = "Ch\u1edd duy\u1ec7t";
    private static final String STATUS_APPROVED = "\u0110\u00e3 duy\u1ec7t";
    private static final String STATUS_REJECTED = "T\u1eeb ch\u1ed1i";

    @FXML private TableView<Row> lossTable;
    @FXML private TableColumn<Row, String> codeColumn;
    @FXML private TableColumn<Row, String> creatorColumn;
    @FXML private TableColumn<Row, String> materialColumn;
    @FXML private TableColumn<Row, String> quantityColumn;
    @FXML private TableColumn<Row, String> reasonColumn;
    @FXML private TableColumn<Row, String> dateColumn;
    @FXML private TableColumn<Row, String> statusColumn;
    @FXML private TableColumn<Row, Void> actionColumn;
    @FXML private StackPane lossTabIconContainer;
    @FXML private StackPane detailTabIconContainer;

    private final ObservableList<Row> rows = FXCollections.observableArrayList(
            new Row("HH-001", "NV_002", "S\u1eefa t\u01b0\u01a1i", "2 L", "\u0110\u1ed5 v\u1ee1 trong ca", "19/07/2026", STATUS_PENDING),
            new Row("HH-002", "NV_003", "C\u00e0 ph\u00ea h\u1ea1t", "1.5 kg", "Hao h\u1ee5t ki\u1ec3m k\u00ea", "18/07/2026", STATUS_APPROVED),
            new Row("HH-003", "NV_002", "Ly gi\u1ea5y", "20 c\u00e1i", "H\u01b0 h\u1ecfng bao b\u00ec", "17/07/2026", STATUS_REJECTED)
    );

    @FXML
    private void initialize() {
        lossTabIconContainer.getChildren().setAll(IconFactory.createPageTabIcon("alert"));
        detailTabIconContainer.getChildren().setAll(IconFactory.createPageTabIcon("clipboard"));

        codeColumn.setCellValueFactory(data -> data.getValue().codeProperty());
        creatorColumn.setCellValueFactory(data -> data.getValue().creatorProperty());
        materialColumn.setCellValueFactory(data -> data.getValue().materialProperty());
        quantityColumn.setCellValueFactory(data -> data.getValue().quantityProperty());
        reasonColumn.setCellValueFactory(data -> data.getValue().reasonProperty());
        dateColumn.setCellValueFactory(data -> data.getValue().dateProperty());
        statusColumn.setCellValueFactory(data -> data.getValue().statusProperty());

        codeColumn.setCellFactory(column -> new CodeCell());
        creatorColumn.setCellFactory(column -> new CreatorCell());
        materialColumn.setCellFactory(column -> new WrapTextCell<>(160));
        quantityColumn.setCellFactory(column -> new QuantityCell());
        reasonColumn.setCellFactory(column -> new WrapTextCell<>(230));
        statusColumn.setCellFactory(column -> new StatusCell());
        actionColumn.setCellFactory(column -> new ActionCell());

        lossTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        lossTable.setItems(rows);
    }

    private void approve(Row row) {
        row.setStatus(STATUS_APPROVED);
        lossTable.refresh();
    }

    private void reject(Row row) {
        row.setStatus(STATUS_REJECTED);
        lossTable.refresh();
    }

    private void showDetails(Row row) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Chi ti\u1ebft hao h\u1ee5t");
        alert.setHeaderText(row.getCode() + " - " + row.getStatus());
        alert.setContentText("Ng\u01b0\u1eddi l\u1eadp: " + row.getCreator()
                + "\nNguy\u00ean li\u1ec7u: " + row.getMaterial()
                + "\nS\u1ed1 l\u01b0\u1ee3ng: " + row.getQuantity()
                + "\nL\u00fd do: " + row.getReason()
                + "\nNg\u00e0y ghi nh\u1eadn: " + row.getDate());
        alert.showAndWait();
    }

    private static String statusStyle(String status) {
        return switch (status) {
            case STATUS_APPROVED -> "badge-approved";
            case STATUS_REJECTED -> "badge-rejected";
            default -> "badge-pending";
        };
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
            Label label = new Label(code);
            label.getStyleClass().add("cell-code");
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
        private final double maxWidth;

        private WrapTextCell(double maxWidth) {
            this.maxWidth = maxWidth;
        }

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
            label.setMaxWidth(maxWidth);
            label.setAlignment(Pos.CENTER);
            setGraphic(label);
            setText(null);
            setAlignment(Pos.CENTER);
        }
    }

    private class QuantityCell extends TableCell<Row, String> {
        @Override
        protected void updateItem(String value, boolean empty) {
            super.updateItem(value, empty);
            if (empty || value == null) {
                setGraphic(null);
                setText(null);
                return;
            }
            Label label = new Label(value);
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
        private final SimpleStringProperty material;
        private final SimpleStringProperty quantity;
        private final SimpleStringProperty reason;
        private final SimpleStringProperty date;
        private final SimpleStringProperty status;

        public Row(String code, String creator, String material, String quantity, String reason, String date, String status) {
            this.code = new SimpleStringProperty(code);
            this.creator = new SimpleStringProperty(creator);
            this.material = new SimpleStringProperty(material);
            this.quantity = new SimpleStringProperty(quantity);
            this.reason = new SimpleStringProperty(reason);
            this.date = new SimpleStringProperty(date);
            this.status = new SimpleStringProperty(status);
        }

        public SimpleStringProperty codeProperty() { return code; }
        public SimpleStringProperty creatorProperty() { return creator; }
        public SimpleStringProperty materialProperty() { return material; }
        public SimpleStringProperty quantityProperty() { return quantity; }
        public SimpleStringProperty reasonProperty() { return reason; }
        public SimpleStringProperty dateProperty() { return date; }
        public SimpleStringProperty statusProperty() { return status; }
        public String getCode() { return code.get(); }
        public String getCreator() { return creator.get(); }
        public String getMaterial() { return material.get(); }
        public String getQuantity() { return quantity.get(); }
        public String getReason() { return reason.get(); }
        public String getDate() { return date.get(); }
        public String getStatus() { return status.get(); }
        public void setStatus(String value) { status.set(value); }
    }
}
