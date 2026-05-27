INSERT INTO
    chi_nhanh (
        chi_nhanh_id,
        ten_chi_nhanh,
        dia_chi,
        phone,
        trang_thai
    )
VALUES (
        'CN001',
        N'Chi nhánh trung tâm',
        N'123 Nguyễn Huệ, Quận 1',
        '0900000001',
        1
    );

INSERT INTO
    app_user (
        user_id,
        ten_dang_nhap,
        mat_khau,
        vai_tro,
        trang_thai
    )
VALUES (
        'U001',
        'admin',
        '123456',
        'IT_ADMIN',
        1
    );

INSERT INTO
    nhan_vien (
        nhan_vien_id,
        user_id,
        chi_nhanh_id,
        ho_ten,
        cccd,
        email,
        phone,
        chuc_vu,
        trang_thai,
        ngay_vao_lam,
        ghi_chu
    )
VALUES (
        'NV001',
        'U001',
        'CN001',
        N'Quản trị viên',
        '000000000001',
        'admin@phungloccoffee.local',
        '0900000002',
        'IT_ADMIN',
        1,
        SYSTIMESTAMP,
        N'Tài khoản quản trị mẫu'
    );

INSERT INTO
    kho (
        kho_id,
        chi_nhanh_id,
        ten_kho,
        trang_thai
    )
VALUES (
        'KHO001',
        'CN001',
        N'Kho trung tâm',
        1
    );

INSERT INTO
    nha_cung_cap (
        nha_cung_cap_id,
        ten_nha_cung_cap,
        so_dien_thoai,
        email,
        trang_thai
    )
VALUES (
        'NCC001',
        N'Nhà cung cấp mẫu',
        '0900000003',
        'ncc@example.com',
        'CON_HOP_TAC'
    );

INSERT INTO
    danh_muc_san_pham (
        danh_muc_id,
        ten_danh_muc,
        mo_ta
    )
VALUES (
        'DM001',
        N'Cà phê',
        N'Đồ uống cà phê thành phẩm'
    );

INSERT INTO
    danh_muc_san_pham (
        danh_muc_id,
        ten_danh_muc,
        mo_ta
    )
VALUES (
        'DM002',
        N'Nguyên liệu',
        N'Nguyên liệu pha chế'
    );

INSERT INTO
    san_pham (
        san_pham_id,
        danh_muc_id,
        ten_san_pham,
        loai_san_pham,
        don_vi_tinh,
        gia_ban,
        gia_von,
        trang_thai
    )
VALUES (
        'SP001',
        'DM001',
        N'Cà phê sữa',
        'THANH_PHAM',
        'ML',
        29000,
        12000,
        1
    );

INSERT INTO
    san_pham (
        san_pham_id,
        danh_muc_id,
        ten_san_pham,
        loai_san_pham,
        don_vi_tinh,
        gia_ban,
        gia_von,
        trang_thai
    )
VALUES (
        'SP002',
        'DM001',
        N'Bạc xỉu',
        'THANH_PHAM',
        'ML',
        32000,
        14000,
        1
    );

INSERT INTO
    san_pham (
        san_pham_id,
        danh_muc_id,
        ten_san_pham,
        loai_san_pham,
        don_vi_tinh,
        gia_ban,
        gia_von,
        trang_thai
    )
VALUES (
        'NL001',
        'DM002',
        N'Hạt cà phê rang',
        'NGUYEN_LIEU',
        'KG',
        0,
        180000,
        1
    );

INSERT INTO
    san_pham (
        san_pham_id,
        danh_muc_id,
        ten_san_pham,
        loai_san_pham,
        don_vi_tinh,
        gia_ban,
        gia_von,
        trang_thai
    )
VALUES (
        'NL002',
        'DM002',
        N'Sữa đặc',
        'NGUYEN_LIEU',
        'KG',
        0,
        45000,
        1
    );

INSERT INTO
    ton_kho (
        kho_id,
        san_pham_id,
        so_luong_ton,
        muc_ton_toi_thieu
    )
VALUES ('KHO001', 'SP001', 100, 10);

INSERT INTO
    ton_kho (
        kho_id,
        san_pham_id,
        so_luong_ton,
        muc_ton_toi_thieu
    )
VALUES ('KHO001', 'SP002', 100, 10);

INSERT INTO
    ton_kho (
        kho_id,
        san_pham_id,
        so_luong_ton,
        muc_ton_toi_thieu
    )
VALUES ('KHO001', 'NL001', 25, 5);

INSERT INTO
    ton_kho (
        kho_id,
        san_pham_id,
        so_luong_ton,
        muc_ton_toi_thieu
    )
VALUES ('KHO001', 'NL002', 30, 5);

COMMIT;