package com.phungloccoffee.dao;

import com.phungloccoffee.exception.DatabaseException;
import com.phungloccoffee.model.KiemKeKho;

import java.util.List;

public class KiemKeKhoDAO {
    private final InventoryAuditDAO inventoryAuditDAO = new InventoryAuditDAO();

    public List<? extends KiemKeKho> findRecent() throws DatabaseException {
        return List.of();
    }
}
