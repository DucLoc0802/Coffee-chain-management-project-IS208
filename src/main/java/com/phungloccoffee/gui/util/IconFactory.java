package com.phungloccoffee.gui.util;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.SVGPath;

public final class IconFactory {
    private static final String HOME = "M3 11 L12 3 L21 11 M5 10 V21 H10 V15 H14 V21 H19 V10";
    private static final String ARROW_DOWN = "M12 3 V16 M7 11 L12 16 L17 11 M5 21 H19";
    private static final String ARROW_UP = "M12 21 V8 M7 13 L12 8 L17 13 M5 3 H19";
    private static final String ARROW_LEFT = "M19 12 H5 M11 6 L5 12 L11 18";
    private static final String REPEAT = "M17 1 L21 5 L17 9 M21 5 H7 C4.8 5 3 6.8 3 9 M7 23 L3 19 L7 15 M3 19 H17 C19.2 19 21 17.2 21 15";
    private static final String ALERT = "M12 3 L22 20 H2 Z M12 9 V14 M12 18 H12.01";
    private static final String USER = "M20 21 C20 17.7 16.4 15 12 15 C7.6 15 4 17.7 4 21 M12 12 C14.2 12 16 10.2 16 8 C16 5.8 14.2 4 12 4 C9.8 4 8 5.8 8 8 C8 10.2 9.8 12 12 12";
    private static final String USERS = "M16 21 C16 18.8 13.8 17 11 17 C8.2 17 6 18.8 6 21 M11 14 C13.2 14 15 12.2 15 10 C15 7.8 13.2 6 11 6 C8.8 6 7 7.8 7 10 C7 12.2 8.8 14 11 14 M19 21 C19 19.4 17.9 18.1 16.3 17.4 M16.8 6.4 C18.6 6.9 20 8.5 20 10.5 C20 12.4 18.7 14 17 14.5";
    private static final String CLIPBOARD = "M9 4 H15 M9 4 C9 3 10 2 12 2 C14 2 15 3 15 4 M9 4 H7 C5.9 4 5 4.9 5 6 V20 C5 21.1 5.9 22 7 22 H17 C18.1 22 19 21.1 19 20 V6 C19 4.9 18.1 4 17 4 H15 M8 10 H16 M8 14 H16 M8 18 H13";
    private static final String CLIPBOARD_CHECK = "M9 4 H15 M9 4 C9 3 10 2 12 2 C14 2 15 3 15 4 M9 4 H7 C5.9 4 5 4.9 5 6 V20 C5 21.1 5.9 22 7 22 H17 C18.1 22 19 21.1 19 20 V6 C19 4.9 18.1 4 17 4 H15 M8 13 L11 16 L16 10";
    private static final String LIST = "M8 6 H21 M8 12 H21 M8 18 H21 M3 6 H3.01 M3 12 H3.01 M3 18 H3.01";
    private static final String CIRCLE = "M12 21 C17 21 21 17 21 12 C21 7 17 3 12 3 C7 3 3 7 3 12 C3 17 7 21 12 21";
    private static final String REFRESH = "M20 6 V12 H14 M4 18 V12 H10 M18.5 9 C17.4 6.6 15 5 12.2 5 C8.2 5 5 8.1 5 12 M5.5 15 C6.6 17.4 9 19 11.8 19 C15.8 19 19 15.9 19 12";
    private static final String DOWNLOAD = "M12 3 V15 M7 10 L12 15 L17 10 M5 21 H19";
    private static final String PLUS = "M12 5 V19 M5 12 H19";
    private static final String SAVE = "M5 3 H17 L21 7 V21 H3 V3 H5 M7 3 V9 H16 V3 M7 21 V15 H17 V21";
    private static final String CLOCK = "M12 21 C17 21 21 17 21 12 C21 7 17 3 12 3 C7 3 3 7 3 12 C3 17 7 21 12 21 M12 7 V12 L15 14";
    private static final String MONEY = "M12 2 V22 M17 5 H9.5 C7.6 5 6 6.2 6 8 C6 10.1 8 10.8 12 12 C16 13.2 18 13.9 18 16 C18 17.8 16.4 19 14.5 19 H7";
    private static final String RECEIPT = "M6 3 H18 V21 L15 19 L12 21 L9 19 L6 21 V3 M9 8 H15 M9 12 H15 M9 16 H13";
    private static final String TREND = "M3 17 L9 11 L13 15 L21 7 M16 7 H21 V12";
    private static final String PACKAGE = "M21 8 L12 3 L3 8 V18 L12 23 L21 18 V8 M3 8 L12 13 L21 8 M12 13 V23";
    private static final String CART = "M3 4 H5 L7 15 H18 L21 7 H6 M9 20 H9.01 M17 20 H17.01";
    private static final String CREDIT_CARD = "M3 6 H21 V18 H3 V6 M3 10 H21 M7 15 H11";
    private static final String WIFI_OFF = "M3 3 L21 21 M8.5 13.5 C10.5 12.1 13.5 12.1 15.5 13.5 M5 10 C6.2 8.8 7.7 7.9 9.3 7.4 M14.8 7.5 C16.4 8 17.9 8.9 19 10 M12 18 H12.01";
    private static final String TRUCK = "M3 7 H14 V17 H3 V7 M14 10 H18 L21 13 V17 H14 V10 M7 20 C8.1 20 9 19.1 9 18 C9 16.9 8.1 16 7 16 C5.9 16 5 16.9 5 18 C5 19.1 5.9 20 7 20 M18 20 C19.1 20 20 19.1 20 18 C20 16.9 19.1 16 18 16 C16.9 16 16 16.9 16 18 C16 19.1 16.9 20 18 20";
    private static final String SHIELD = "M12 22 C17 19.5 20 15.5 20 9 V5 L12 2 L4 5 V9 C4 15.5 7 19.5 12 22 M9 12 L11 14 L15 10";
    private static final String BUILDING = "M4 21 H20 M6 21 V5 H18 V21 M9 8 H10 M14 8 H15 M9 12 H10 M14 12 H15 M9 16 H10 M14 16 H15";
    private static final String MONITOR = "M4 5 H20 V16 H4 V5 M9 21 H15 M12 16 V21";
    private static final String SETTINGS = "M12 15 C13.7 15 15 13.7 15 12 C15 10.3 13.7 9 12 9 C10.3 9 9 10.3 9 12 C9 13.7 10.3 15 12 15 M19.4 15 A8 8 0 0 0 19.4 9 L21 7 L19 4 L16.5 5 A8 8 0 0 0 11.5 3 L10 1 H6 L4.5 4 A8 8 0 0 0 3 7 L1 8 V12 L4 13.5 A8 8 0 0 0 5.5 18 L4 20 L6 23 H10 L11.5 20 A8 8 0 0 0 16.5 19 L19 20 L21 17 Z";
    private static final String COFFEE = "M4 8 H16 V14 C16 17 13.8 19 10 19 C6.2 19 4 17 4 14 V8 M16 10 H18 C19.7 10 21 11.3 21 13 C21 14.7 19.7 16 18 16 H16 M3 21 H19 M6 3 V5 M10 3 V5 M14 3 V5";
    private static final String RECIPE = "M6 3 H17 L20 6 V21 H6 V3 M17 3 V7 H20 M9 11 H17 M9 15 H17 M9 19 H14";

    private IconFactory() {
    }

    public static Node createMenuIcon(String menuId) {
        return createIcon(menuPath(menuId), "menu-icon-box", "menu-icon-path");
    }

    public static Node createTopbarIcon(String type) {
        return createIcon(topbarPath(type), "topbar-icon-box", "topbar-icon-path");
    }

    public static Node createActionIcon(String type) {
        return createIcon(topbarPath(type), "topbar-icon-box", "topbar-action-icon-path");
    }

    public static Node createPageTabIcon(String type) {
        return createIcon(topbarPath(type), "page-tab-icon-box", "page-tab-icon-path");
    }

    public static Node createReportIcon(String type) {
        return createIcon(topbarPath(type), "report-icon-box", "report-icon-path");
    }

    private static Node createIcon(String content, String boxClass, String pathClass) {
        SVGPath path = new SVGPath();
        path.setContent(content);
        path.getStyleClass().add(pathClass);
        StackPane box = new StackPane(path);
        box.getStyleClass().add(boxClass);
        return box;
    }

    private static String menuPath(String menuId) {
        return switch (menuId) {
            case "BRANCH_DASHBOARD", "DIRECTOR_DASHBOARD" -> HOME;
            case "BRANCH_MANAGEMENT" -> BUILDING;
            case "POS_MAIN" -> CART;
            case "POS_PAYMENT" -> CREDIT_CARD;
            case "POS_HISTORY" -> CLOCK;
            case "CUSTOMER" -> USER;
            case "OFFLINE_TRANSACTION" -> WIFI_OFF;
            case "APPROVE_IMPORT", "WAREHOUSE_IMPORT" -> ARROW_DOWN;
            case "APPROVE_EXPORT", "WAREHOUSE_EXPORT" -> ARROW_UP;
            case "APPROVE_TRANSFER", "WAREHOUSE_TRANSFER" -> REPEAT;
            case "APPROVE_MATERIAL_LOSS", "MATERIAL_LOSS" -> ALERT;
            case "SUPPLIER" -> TRUCK;
            case "INVENTORY_AUDIT" -> CLIPBOARD_CHECK;
            case "BRANCH_EMPLOYEE", "ACCOUNT_LIST" -> USERS;
            case "BRANCH_REVENUE_REPORT", "REVENUE_REPORT" -> TREND;
            case "BRANCH_INVENTORY_REPORT", "INVENTORY_REPORT", "INVENTORY_LIST" -> PACKAGE;
            case "BEST_SELLER_REPORT" -> TREND;
            case "PRODUCT_MANAGEMENT" -> COFFEE;
            case "PRODUCT_RECIPE" -> RECIPE;
            case "ACCOUNT_EDIT" -> SAVE;
            case "PERMISSION" -> SHIELD;
            case "POS_DEVICE" -> MONITOR;
            case "SYSTEM_CONFIG" -> SETTINGS;
            default -> CIRCLE;
        };
    }

    private static String topbarPath(String type) {
        return switch (type) {
            case "refresh" -> REFRESH;
            case "download" -> DOWNLOAD;
            case "plus" -> PLUS;
            case "save" -> SAVE;
            case "arrow-down" -> ARROW_DOWN;
            case "arrow-up" -> ARROW_UP;
            case "back", "arrow-left" -> ARROW_LEFT;
            case "repeat", "transfer" -> REPEAT;
            case "alert", "alert-triangle" -> ALERT;
            case "clipboard" -> CLIPBOARD;
            case "clipboard-check" -> CLIPBOARD_CHECK;
            case "clock" -> CLOCK;
            case "money" -> MONEY;
            case "receipt" -> RECEIPT;
            case "trend" -> TREND;
            case "package" -> PACKAGE;
            case "cart" -> CART;
            case "list" -> LIST;
            case "credit-card" -> CREDIT_CARD;
            case "wifi-off" -> WIFI_OFF;
            case "truck" -> TRUCK;
            case "shield" -> SHIELD;
            case "building", "store" -> BUILDING;
            case "monitor" -> MONITOR;
            case "settings" -> SETTINGS;
            case "coffee" -> COFFEE;
            case "recipe" -> RECIPE;
            default -> HOME;
        };
    }
}
