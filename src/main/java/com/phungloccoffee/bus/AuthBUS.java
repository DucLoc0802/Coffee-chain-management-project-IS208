package com.phungloccoffee.bus;

import com.phungloccoffee.dao.AppUserDAO;
import com.phungloccoffee.dao.NhanVienDAO;
import com.phungloccoffee.exception.DatabaseException;
import com.phungloccoffee.exception.ValidationException;
import com.phungloccoffee.model.AppUser;
import com.phungloccoffee.model.NhanVien;
import com.phungloccoffee.util.PasswordUtil;
import com.phungloccoffee.util.SessionManager;
import com.phungloccoffee.util.ValidationUtils;

public class AuthBUS {
    private final AppUserDAO appUserDAO = new AppUserDAO();
    private final NhanVienDAO nhanVienDAO = new NhanVienDAO();

    public AppUser login(String username, String password) throws ValidationException, DatabaseException {
        String cleanUsername = username == null ? "" : username.trim();
        String cleanPassword = password == null ? "" : password.trim();

        ValidationUtils.requireText(cleanUsername, "Tên đăng nhập");
        ValidationUtils.requireText(cleanPassword, "Mật khẩu");

        AppUser user = appUserDAO.findByUsername(cleanUsername)
                .orElseThrow(() -> new ValidationException("Tài khoản không tồn tại."));
        if (!PasswordUtil.verifyPassword(cleanPassword, user.getMatKhau())) {
            throw new ValidationException("Mật khẩu không đúng.");
        }
        if (user.getTrangThai() == 0) {
            throw new ValidationException("Tài khoản đã bị khóa.");
        }

        NhanVien nhanVien = nhanVienDAO.findByUserId(user.getUserId()).orElse(null);
        appUserDAO.updateLastLogin(user.getUserId());
        SessionManager.login(
                user.getUserId(),
                user.getTenDangNhap(),
                user.getVaiTro(),
                nhanVien == null ? null : nhanVien.getNhanVienId(),
                nhanVien == null ? user.getTenDangNhap() : nhanVien.getHoTen(),
                nhanVien == null ? null : nhanVien.getChiNhanhId()
        );
        return user;
    }
}
