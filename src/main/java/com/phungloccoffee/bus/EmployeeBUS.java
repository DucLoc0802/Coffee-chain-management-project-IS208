package com.phungloccoffee.bus;

import com.phungloccoffee.dao.AppUserDAO;
import com.phungloccoffee.dao.ChiNhanhDAO;
import com.phungloccoffee.dao.EmployeeDAO;
import com.phungloccoffee.dao.NhanVienDAO;
import com.phungloccoffee.exception.DatabaseException;
import com.phungloccoffee.exception.PermissionException;
import com.phungloccoffee.exception.ValidationException;
import com.phungloccoffee.model.Employee;
import com.phungloccoffee.model.NhanVien;
import com.phungloccoffee.util.ValidationUtils;

import java.util.List;
import java.util.Set;

public class EmployeeBUS extends PermissionBUS {
    private static final Set<String> VALID_EMPLOYEE_POSITIONS = Set.of(
            "THU_NGAN",
            "QUAN_LY_CHI_NHANH",
            "NHAN_VIEN_KHO",
            "IT_ADMIN",
            "BAN_GIAM_DOC",
            "NHAN_VIEN_PHA_CHE",
            "NHAN_VIEN_PHUC_VU"
    );

    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    private final NhanVienDAO nhanVienDAO = new NhanVienDAO();
    private final ChiNhanhDAO chiNhanhDAO = new ChiNhanhDAO();
    private final AppUserDAO appUserDAO = new AppUserDAO();

    public List<Employee> loadEmployees() throws DatabaseException, PermissionException {
        requireRole("QUAN_LY_CHI_NHANH", "IT_ADMIN");
        return employeeDAO.findAll();
    }

    public void createEmployee(NhanVien employee)
            throws DatabaseException, PermissionException, ValidationException {
        requireRole("QUAN_LY_CHI_NHANH", "IT_ADMIN");
        validateEmployeeForInsert(employee, true);
        nhanVienDAO.insert(employee);
    }

    public void updateEmployee(NhanVien employee)
            throws DatabaseException, PermissionException, ValidationException {
        requireRole("QUAN_LY_CHI_NHANH", "IT_ADMIN");
        validateEmployeeForInsert(employee, false);
        nhanVienDAO.update(employee);
    }

    void validateEmployeeForInsert(NhanVien employee, boolean requireExistingUser)
            throws ValidationException, DatabaseException {
        if (employee == null) {
            throw new ValidationException("Thông tin nhân viên không hợp lệ.");
        }
        ValidationUtils.requireText(employee.getNhanVienId(), "Mã nhân viên");
        ValidationUtils.requireText(employee.getHoTen(), "Họ tên");
        ValidationUtils.requireText(employee.getChucVu(), "Chức vụ");

        if (employee.getChiNhanhId() == null || employee.getChiNhanhId().trim().isEmpty()) {
            throw new ValidationException("Vui lòng chọn chi nhánh");
        }
        if (!chiNhanhDAO.existsById(employee.getChiNhanhId())) {
            throw new ValidationException("Chi nhánh không tồn tại: " + employee.getChiNhanhId());
        }
        if (!VALID_EMPLOYEE_POSITIONS.contains(employee.getChucVu())) {
            throw new ValidationException("Chức vụ nhân viên không hợp lệ.");
        }
        if (employee.getTrangThai() != 0 && employee.getTrangThai() != 1) {
            throw new ValidationException("Trạng thái nhân viên không hợp lệ.");
        }
        if (requireExistingUser && employee.getUserId() != null && !employee.getUserId().trim().isEmpty()
                && !appUserDAO.existsById(employee.getUserId())) {
            throw new ValidationException("Tài khoản người dùng không tồn tại: " + employee.getUserId());
        }
    }
}
