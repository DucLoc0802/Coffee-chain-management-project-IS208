package com.phungloccoffee.gui.service;

import com.phungloccoffee.gui.model.AppUserSession;

public final class SessionManager {
    private static AppUserSession currentUser;

    private SessionManager() {
    }

    public static void login(AppUserSession user) {
        currentUser = user;
        com.phungloccoffee.util.SessionManager.login(
                user.getUserId(),
                user.getUsername(),
                user.getRole().name(),
                user.getEmployeeId(),
                user.getFullName(),
                user.getBranchId()
        );
    }

    public static AppUserSession getCurrentUser() {
        return currentUser;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static void logout() {
        currentUser = null;
        com.phungloccoffee.util.SessionManager.logout();
    }
}
