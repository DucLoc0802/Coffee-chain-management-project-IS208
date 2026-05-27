package com.phungloccoffee.bus;

import com.phungloccoffee.exception.PermissionException;
import com.phungloccoffee.util.SessionManager;

public class PermissionBUS {
    protected void requireRole(String... roles) throws PermissionException {
        if (!SessionManager.isLoggedIn()) {
            throw new PermissionException("Bạn cần đăng nhập để sử dụng chức năng này.");
        }
        if (!SessionManager.hasAnyRole(roles)) {
            throw new PermissionException("Bạn không có quyền thực hiện thao tác này.");
        }
    }
}
