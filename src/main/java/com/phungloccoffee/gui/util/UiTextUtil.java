package com.phungloccoffee.gui.util;

import com.phungloccoffee.gui.model.AppUserSession;

public final class UiTextUtil {
    private UiTextUtil() {
    }

    public static String buildUserContext(AppUserSession user) {
        String fullName = safe(user == null ? "" : user.getFullName());
        String roleName = safe(user == null ? "" : user.getRoleName());
        String branchName = safe(user == null ? "" : user.getBranchName());

        if (fullName.isBlank() && roleName.isBlank()) {
            return branchName;
        }
        if (fullName.isBlank()) {
            return join(roleName, branchName);
        }
        if (roleName.isBlank()) {
            return join(fullName, branchName);
        }
        if (fullName.equalsIgnoreCase(roleName)) {
            return join(roleName, branchName);
        }
        return join(fullName, roleName, branchName);
    }

    private static String join(String... parts) {
        StringBuilder builder = new StringBuilder();
        for (String part : parts) {
            String clean = safe(part);
            if (clean.isBlank()) {
                continue;
            }
            if (!builder.isEmpty()) {
                builder.append(" • ");
            }
            builder.append(clean);
        }
        return builder.toString();
    }

    private static String safe(String value) {
        return value == null ? "" : value.trim();
    }
}
