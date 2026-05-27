package com.phungloccoffee.bus;

import com.phungloccoffee.dao.WarehouseTransactionDAO;
import com.phungloccoffee.exception.DatabaseException;
import com.phungloccoffee.exception.PermissionException;
import com.phungloccoffee.exception.ValidationException;
import com.phungloccoffee.model.WarehouseTransaction;

import java.util.List;

public class ExportApprovalBUS extends PermissionBUS {
    private final WarehouseTransactionDAO transactionDAO = new WarehouseTransactionDAO();

    public List<WarehouseTransaction> loadPendingExports() throws DatabaseException, PermissionException {
        requireRole("QUAN_LY_CHI_NHANH", "IT_ADMIN");
        return transactionDAO.findPendingExports();
    }

    public void approve(int transactionId) throws ValidationException, PermissionException, DatabaseException {
        updateStatus(String.valueOf(transactionId), "DA_DUYET");
    }

    public void approve(String transactionId) throws ValidationException, PermissionException, DatabaseException {
        updateStatus(transactionId, "DA_DUYET");
    }

    public void reject(int transactionId) throws ValidationException, PermissionException, DatabaseException {
        updateStatus(String.valueOf(transactionId), "DA_HUY");
    }

    public void reject(String transactionId) throws ValidationException, PermissionException, DatabaseException {
        updateStatus(transactionId, "DA_HUY");
    }

    private void updateStatus(String transactionId, String status) throws ValidationException, PermissionException, DatabaseException {
        requireRole("QUAN_LY_CHI_NHANH", "IT_ADMIN");
        if (transactionId == null || transactionId.isBlank()) {
            throw new ValidationException("Phiếu xuất không hợp lệ.");
        }
        transactionDAO.updateStatus(transactionId, status);
    }
}
