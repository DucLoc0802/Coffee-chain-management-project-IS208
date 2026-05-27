package com.phungloccoffee.bus;

import com.phungloccoffee.dao.AppUserDAO;
import com.phungloccoffee.dao.ChiNhanhDAO;
import com.phungloccoffee.exception.DatabaseException;
import com.phungloccoffee.exception.PermissionException;
import com.phungloccoffee.exception.ValidationException;
import com.phungloccoffee.model.AppUser;
import com.phungloccoffee.model.NhanVien;
import com.phungloccoffee.util.ValidationUtils;

import java.util.List;
import java.util.Set;

public class AccountBUS extends PermissionBUS {
    private static final Set<String> VALID_ACCOUNT_ROLES = Set.of(
            "THU_NGAN",
            "QUAN_LY_CHI_NHANH",
            "NHAN_VIEN_KHO",
            "IT_ADMIN",
            "BAN_GIAM_DOC"
    );

    private static final Set<String> VALID_EMPLOYEE_POSITIONS = Set.of(
            "THU_NGAN",
            "QUAN_LY_CHI_NHANH",
            "NHAN_VIEN_KHO",
            "IT_ADMIN",
            "BAN_GIAM_DOC",
            "NHAN_VIEN_PHA_CHE",
            "NHAN_VIEN_PHUC_VU"
    );

    private final AppUserDAO appUserDAO = new AppUserDAO();
    private final ChiNhanhDAO chiNhanhDAO = new ChiNhanhDAO();

    public List<AppUser> loadAccounts() throws DatabaseException, PermissionException {
        requireRole("IT_ADMIN");
        return appUserDAO.findAll();
    }

    public void createAccountWithEmployee(AppUser user, NhanVien employee)
            throws ValidationException, PermissionException, DatabaseException {
        requireRole("IT_ADMIN");
        validateAccountForCreate(user);
        validateEmployeeForAccountCreate(user, employee);
        appUserDAO.createAccountWithEmployee(user, employee);
    }

    public void updateAccount(AppUser user) throws ValidationException, PermissionException, DatabaseException {
        requireRole("IT_ADMIN");
        ValidationUtils.requireText(user.getUserId(), "Mã tài khoản");
        ValidationUtils.requireText(user.getVaiTro(), "Vai trò");
        if (!VALID_ACCOUNT_ROLES.contains(user.getVaiTro())) {
            throw new ValidationException("Vai trò không hợp lệ.");
        }
        if (user.getTrangThai() != 0 && user.getTrangThai() != 1) {
            throw new ValidationException("Trạng thái tài khoản không hợp lệ.");
        }
        appUserDAO.update(user);
    }

    private void validateAccountForCreate(AppUser user) throws ValidationException {
        if (user == null) {
            throw new ValidationException("Thông tin tài khoản không hợp lệ.");
        }
        ValidationUtils.requireText(user.getUserId(), "Mã tài khoản");
        ValidationUtils.requireText(user.getTenDangNhap(), "Tên đăng nhập");
        ValidationUtils.requireText(user.getMatKhau(), "Mật khẩu");
        ValidationUtils.requireText(user.getVaiTro(), "Vai trò");
        if (!VALID_ACCOUNT_ROLES.contains(user.getVaiTro())) {
            throw new ValidationException("Vai trò không hợp lệ.");
        }
        if (user.getTrangThai() != 0 && user.getTrangThai() != 1) {
            throw new ValidationException("Trạng thái tài khoản không hợp lệ.");
        }
    }

    private void validateEmployeeForAccountCreate(AppUser user, NhanVien employee)
            throws ValidationException, DatabaseException {
        if (employee == null) {
            throw new ValidationException("Thông tin nhân viên không hợp lệ.");
        }
        ValidationUtils.requireText(employee.getNhanVienId(), "Mã nhân viên");
        ValidationUtils.requireText(employee.getHoTen(), "Họ tên");
        ValidationUtils.requireText(employee.getChucVu(), "Chức vụ");

        if (employee.getUserId() == null || employee.getUserId().trim().isEmpty()) {
            employee.setUserId(user.getUserId());
        }
        if (!user.getUserId().equals(employee.getUserId())) {
            throw new ValidationException("Tài khoản liên kết của nhân viên không khớp.");
        }
        if (employee.getChiNhanhId() == null || employee.getChiNhanhId().trim().isEmpty()) {
            throw new ValidationException("Vui lòng chọn chi nhánh hợp lệ");
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
    }
}
