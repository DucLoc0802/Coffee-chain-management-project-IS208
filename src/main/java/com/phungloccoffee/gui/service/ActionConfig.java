package com.phungloccoffee.gui.service;

import com.phungloccoffee.gui.util.IconFactory;
import javafx.scene.control.Button;

import java.util.List;
import java.util.Map;
import java.util.Set;

public final class ActionConfig {
    private static final Map<String, String> ACTIONS = Map.ofEntries(
            Map.entry("POS_PAYMENT", "L\u00e0m m\u1edbi"),
            Map.entry("POS_HISTORY", "Xu\u1ea5t l\u1ecbch s\u1eed"),
            Map.entry("CUSTOMER", "Th\u00eam kh\u00e1ch h\u00e0ng"),
            Map.entry("INVENTORY_LIST", "Xu\u1ea5t Excel"),
            Map.entry("APPROVE_IMPORT", "L\u00e0m m\u1edbi danh s\u00e1ch"),
            Map.entry("APPROVE_EXPORT", "L\u00e0m m\u1edbi danh s\u00e1ch"),
            Map.entry("BRANCH_REVENUE_REPORT", "Xu\u1ea5t b\u00e1o c\u00e1o"),
            Map.entry("BRANCH_INVENTORY_REPORT", "Xu\u1ea5t Excel"),
            Map.entry("REVENUE_REPORT", "Xu\u1ea5t b\u00e1o c\u00e1o"),
            Map.entry("ACCOUNT_LIST", "Th\u00eam t\u00e0i kho\u1ea3n")
    );

    private static final Set<String> TEXT_ONLY_ACTIONS = Set.of(
            "APPROVE_IMPORT",
            "APPROVE_EXPORT"
    );

    private ActionConfig() {
    }

    public static List<Button> getActionsFor(String menuId) {
        String label = ACTIONS.get(menuId);
        if (label == null) {
            return List.of();
        }

        Button button = new Button(label);
        if (!TEXT_ONLY_ACTIONS.contains(menuId)) {
            button.setGraphic(IconFactory.createActionIcon(actionIcon(label)));
        }
        button.getStyleClass().add("topbar-action-button");
        button.setOnAction(event -> System.out.println("[ACTION] " + button.getText()));
        return List.of(button);
    }

    private static String actionIcon(String label) {
        String lower = label.toLowerCase();
        if (lower.contains("l\u00e0m m\u1edbi")) {
            return "refresh";
        }
        if (lower.contains("xu\u1ea5t")) {
            return "download";
        }
        if (lower.contains("th\u00eam") || lower.contains("t\u1ea1o")) {
            return "plus";
        }
        if (lower.contains("l\u01b0u")) {
            return "save";
        }
        return "plus";
    }
}
