package com.ktxfood.controller;

import com.ktxfood.dto.AdminLoginRequest;
import com.ktxfood.model.Admin;
import com.ktxfood.service.AdminAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/auth")
public class AdminAuthController {

    @Autowired
    private AdminAuthService adminAuthService;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody AdminLoginRequest request) {
        Admin admin = adminAuthService.login(request);

        Map<String, String> response = new HashMap<>();
        response.put("id", admin.getId());
        response.put("fullName", admin.getFullName());
        response.put("role", admin.getRole());
        return response;
    }
}