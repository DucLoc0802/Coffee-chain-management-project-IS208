package com.phungloccoffee.gui.model;

public class MenuItemModel {
    private final String id;
    private final String title;
    private final String iconText;
    private final String breadcrumb;
    private final String pageTitle;
    private final String fxmlPath;
    private final boolean enabled;

    public MenuItemModel(String id, String title, String iconText, String breadcrumb,
                         String pageTitle, String fxmlPath, boolean enabled) {
        this.id = id;
        this.title = title;
        this.iconText = iconText;
        this.breadcrumb = breadcrumb;
        this.pageTitle = pageTitle;
        this.fxmlPath = fxmlPath;
        this.enabled = enabled;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getIconText() { return iconText; }
    public String getBreadcrumb() { return breadcrumb; }
    public String getPageTitle() { return pageTitle; }
    public String getFxmlPath() { return fxmlPath; }
    public boolean isEnabled() { return enabled; }
}
