package com.phungloccoffee.dao;

import com.phungloccoffee.exception.DatabaseException;
import com.phungloccoffee.model.Employee;
import com.phungloccoffee.model.NhanVien;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    private final NhanVienDAO nhanVienDAO = new NhanVienDAO();

    public List<Employee> findAll() throws DatabaseException {
        List<Employee> employees = new ArrayList<>();
        for (NhanVien nv : nhanVienDAO.findAll()) {
            employees.add(new Employee(
                    0,
                    nv.getNhanVienId(),
                    nv.getHoTen(),
                    nv.getPhone(),
                    nv.getChiNhanhId(),
                    nv.getChucVu(),
                    nv.getTrangThai() == 1 ? "Đang hoạt động" : "Ngừng hoạt động"
            ));
        }
        return employees;
    }
}
