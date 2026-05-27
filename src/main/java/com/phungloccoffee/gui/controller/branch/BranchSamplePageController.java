package com.phungloccoffee.gui.controller.branch;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class BranchSamplePageController {
    @FXML private Label titleLabel;
    @FXML private TableView<Row> sampleTable;
    @FXML private TableColumn<Row, String> col1;
    @FXML private TableColumn<Row, String> col2;
    @FXML private TableColumn<Row, String> col3;
    @FXML private TableColumn<Row, String> col4;

    @FXML
    private void initialize() {
        col1.setCellValueFactory(data -> data.getValue().firstProperty());
        col2.setCellValueFactory(data -> data.getValue().secondProperty());
        col3.setCellValueFactory(data -> data.getValue().thirdProperty());
        col4.setCellValueFactory(data -> data.getValue().fourthProperty());
        sampleTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        seedRows();
    }

    @FXML
    private void handlePrimaryAction() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titleLabel.getText());
        alert.setHeaderText("Thông tin chức năng");
        alert.setContentText("Nội dung nghiệp vụ của màn hình đang được tải.");
        alert.showAndWait();
    }

    private void seedRows() {
        String title = titleLabel.getText();
        if (title.contains("Dashboard")) {
            col1.setText("Hoạt động");
            col2.setText("Người phụ trách");
            col3.setText("Thời gian");
            col4.setText("Trạng thái");
            sampleTable.setItems(FXCollections.observableArrayList(
                    row("Duyệt phiếu xuất PXK-0012", "Quản lý chi nhánh", "09:15", "Chờ duyệt"),
                    row("Cập nhật tồn kho cảnh báo", "Nhân viên kho", "10:30", "Đang xử lý"),
                    row("Tổng hợp doanh thu ca sáng", "Thu ngân", "11:45", "Hoàn tất")
            ));
        } else if (title.contains("nhập")) {
            col1.setText("Mã phiếu");
            col2.setText("Người lập");
            col3.setText("Nhà cung cấp");
            col4.setText("Trạng thái");
            sampleTable.setItems(FXCollections.observableArrayList(
                    row("PNK-001", "NV_003", "Cà phê Tây Nguyên", "Chờ duyệt"),
                    row("PNK-002", "NV_004", "Sữa Đà Lạt", "Đã duyệt"),
                    row("PNK-003", "NV_003", "Bao bì Việt", "Từ chối")
            ));
        } else if (title.contains("điều chuyển")) {
            col1.setText("Mã phiếu");
            col2.setText("Kho nguồn");
            col3.setText("Kho đích");
            col4.setText("Trạng thái");
            sampleTable.setItems(FXCollections.observableArrayList(
                    row("DCK-001", "Kho trung tâm", "Kho chi nhánh", "Chờ duyệt"),
                    row("DCK-002", "Kho chi nhánh", "Quầy bar", "Đã duyệt"),
                    row("DCK-003", "Kho trung tâm", "Kho phụ", "Chờ duyệt")
            ));
        } else if (title.contains("hao hụt")) {
            col1.setText("Mã phiếu");
            col2.setText("Nguyên liệu");
            col3.setText("Số lượng");
            col4.setText("Lý do");
            sampleTable.setItems(FXCollections.observableArrayList(
                    row("HH-001", "Sữa tươi", "3 lít", "Hết hạn"),
                    row("HH-002", "Hạt arabica", "2 kg", "Ẩm mốc"),
                    row("HH-003", "Ly giấy", "50 cái", "Hư hỏng")
            ));
        } else if (title.contains("doanh thu")) {
            col1.setText("Ngày");
            col2.setText("Doanh thu");
            col3.setText("Đơn hàng");
            col4.setText("Ghi chú");
            sampleTable.setItems(FXCollections.observableArrayList(
                    row("19/07/2026", "18.500.000 VNĐ", "126", "Tăng 12%"),
                    row("18/07/2026", "16.200.000 VNĐ", "108", "Ổn định"),
                    row("17/07/2026", "14.900.000 VNĐ", "96", "Cuối tuần")
            ));
        } else {
            col1.setText("Sản phẩm");
            col2.setText("Tồn kho");
            col3.setText("Mức cảnh báo");
            col4.setText("Trạng thái");
            sampleTable.setItems(FXCollections.observableArrayList(
                    row("Cà phê rang xay", "34 kg", "20 kg", "Ổn định"),
                    row("Sữa tươi", "12 lít", "15 lít", "Tồn thấp"),
                    row("Ly giấy M", "420 cái", "200 cái", "Ổn định")
            ));
        }
    }

    private Row row(String first, String second, String third, String fourth) {
        return new Row(first, second, third, fourth);
    }

    public static class Row {
        private final SimpleStringProperty first;
        private final SimpleStringProperty second;
        private final SimpleStringProperty third;
        private final SimpleStringProperty fourth;

        public Row(String first, String second, String third, String fourth) {
            this.first = new SimpleStringProperty(first);
            this.second = new SimpleStringProperty(second);
            this.third = new SimpleStringProperty(third);
            this.fourth = new SimpleStringProperty(fourth);
        }

        public SimpleStringProperty firstProperty() { return first; }
        public SimpleStringProperty secondProperty() { return second; }
        public SimpleStringProperty thirdProperty() { return third; }
        public SimpleStringProperty fourthProperty() { return fourth; }
    }
}
