package com.phungloccoffee.bus;

import com.phungloccoffee.dao.InventoryAuditDAO;
import com.phungloccoffee.exception.DatabaseException;
import com.phungloccoffee.exception.PermissionException;
import com.phungloccoffee.model.InventoryAudit;

import java.util.List;

public class InventoryAuditBUS extends PermissionBUS {
    private final InventoryAuditDAO auditDAO = new InventoryAuditDAO();

    public List<InventoryAudit> loadRecentAudits() throws DatabaseException, PermissionException {
        requireRole("NHAN_VIEN_KHO", "QUAN_LY_CHI_NHANH", "IT_ADMIN");
        return auditDAO.findRecentAudits();
    }
}
