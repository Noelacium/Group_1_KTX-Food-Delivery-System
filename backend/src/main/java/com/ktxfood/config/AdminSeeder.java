package com.ktxfood.config;

import com.ktxfood.model.Admin;
import com.ktxfood.repository.AdminRepository;
import com.ktxfood.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminSeeder implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public void run(String... args) {
        if (adminRepository.findById("admin").isEmpty()) {
            Admin admin = new Admin("admin", "Quản trị viên", PasswordUtils.hash("admin123"));
            adminRepository.save(admin);
            System.out.println(">>> Đã tạo tài khoản admin mặc định: admin / admin123");
        }
    }
}