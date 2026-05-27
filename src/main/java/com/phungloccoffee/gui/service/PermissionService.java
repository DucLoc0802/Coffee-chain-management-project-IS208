package com.phungloccoffee.gui.service;

import com.phungloccoffee.gui.model.AppRole;

public final class PermissionService {
    private PermissionService() {
    }

    public static boolean canAccess(AppRole role, String menuId) {
        if (role == null || menuId == null) {
            return false;
        }
        return MenuConfig.findMenuItem(role, menuId).isPresent();
    }
}
