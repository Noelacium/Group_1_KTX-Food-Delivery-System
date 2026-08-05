package com.ktxfood.service;

import com.ktxfood.dto.AdminLoginRequest;
import com.ktxfood.exception.InvalidCredentialsException;
import com.ktxfood.model.Admin;
import com.ktxfood.repository.AdminRepository;
import com.ktxfood.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminAuthService {

    @Autowired
    private AdminRepository adminRepository;

    public Admin login(AdminLoginRequest request) {
        Admin admin = adminRepository.findById(request.getAdminId())
                .orElseThrow(() -> new InvalidCredentialsException("Tài khoản hoặc mật khẩu không đúng"));

        if (!PasswordUtils.matches(request.getPassword(), admin.getPassword())) {
            throw new InvalidCredentialsException("Tài khoản hoặc mật khẩu không đúng");
        }

        return admin;
    }
}