package com.phungloccoffee.gui.controller.common;

import com.phungloccoffee.gui.model.AppUserSession;
import com.phungloccoffee.gui.model.MenuItemModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PlaceholderPageController {
    @FXML private Label titleLabel;
    @FXML private Label messageLabel;

    public void setContext(MenuItemModel item, AppUserSession user) {
        titleLabel.setText(item.getPageTitle());
        messageLabel.setText(item.isEnabled()
                ? "Không thể tải nội dung chức năng. Vui lòng kiểm tra lại cấu hình màn hình."
                : "Tài khoản hiện tại chưa có quyền truy cập chức năng này.");
    }
}
