package com.phungloccoffee.gui.controller.layout;

import com.phungloccoffee.gui.model.AppUserSession;
import com.phungloccoffee.gui.model.MenuItemModel;
import com.phungloccoffee.gui.service.MenuConfig;
import com.phungloccoffee.gui.util.IconFactory;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class SidebarController {
    @FXML private Label avatarLabel;
    @FXML private Label userNameLabel;
    @FXML private Label userRoleLabel;
    @FXML private Label branchLabel;
    @FXML private VBox navContainer;

    private final Map<String, Button> navButtons = new HashMap<>();
    private Consumer<MenuItemModel> onNavigate;
    private Runnable onLogout;

    public void setup(AppUserSession user, Consumer<MenuItemModel> onNavigate, Runnable onLogout) {
        this.onNavigate = onNavigate;
        this.onLogout = onLogout;
        renderUserInfo(user);
        renderMenu(MenuConfig.getMenuByRole(user.getRole()));
    }

    private void renderUserInfo(AppUserSession user) {
        String fullName = safe(user.getFullName());
        String roleName = safe(user.getRoleName());
        String branchName = safe(user.getBranchName());
        String displayName = fullName.isBlank() ? roleName : fullName;

        avatarLabel.setText(getAvatarText(displayName));
        userNameLabel.setText(displayName);
        branchLabel.setText(branchName);

        boolean duplicate = !fullName.isBlank()
                && !roleName.isBlank()
                && fullName.equalsIgnoreCase(roleName);
        if (duplicate || roleName.isBlank()) {
            userRoleLabel.setText("");
            userRoleLabel.setVisible(false);
            userRoleLabel.setManaged(false);
        } else {
            userRoleLabel.setText(roleName);
            userRoleLabel.setVisible(true);
            userRoleLabel.setManaged(true);
        }
    }

    private void renderMenu(List<MenuItemModel> menuItems) {
        navContainer.getChildren().clear();
        navButtons.clear();
        for (MenuItemModel item : menuItems) {
            Button button = createNavButton(item);
            navButtons.put(item.getId(), button);
            navContainer.getChildren().add(button);
        }
    }

    private Button createNavButton(MenuItemModel item) {
        Node icon = IconFactory.createMenuIcon(item.getId());
        Label title = new Label(item.getTitle());
        title.getStyleClass().add("nav-title");
        HBox graphic = new HBox(icon, title);
        graphic.getStyleClass().add("nav-graphic");

        Button button = new Button();
        button.setMaxWidth(Double.MAX_VALUE);
        button.setGraphic(graphic);
        button.getStyleClass().add("nav-item");
        button.setDisable(!item.isEnabled());
        button.setOnAction(event -> {
            if (onNavigate != null) {
                onNavigate.accept(item);
            }
            setActive(item.getId());
        });
        return button;
    }

    public void setActive(String menuId) {
        navButtons.forEach((id, button) -> button.getStyleClass().remove("nav-item-active"));
        Button active = navButtons.get(menuId);
        if (active != null && !active.getStyleClass().contains("nav-item-active")) {
            active.getStyleClass().add("nav-item-active");
        }
    }

    @FXML
    private void handleLogout() {
        if (onLogout != null) {
            onLogout.run();
        }
    }

    public void setDashboardController(MainDashboardController dashboardController) {
        // Backward-compatible no-op for the previous dashboard controller.
    }

    private String safe(String value) {
        return value == null ? "" : value.trim();
    }

    private String getAvatarText(String value) {
        String clean = safe(value);
        return clean.isEmpty() ? "?" : clean.substring(0, 1).toUpperCase();
    }
}
