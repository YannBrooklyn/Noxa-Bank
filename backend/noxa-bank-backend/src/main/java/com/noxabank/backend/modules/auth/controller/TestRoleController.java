package com.noxabank.backend.modules.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRoleController {
    
    @GetMapping("/api/user/test")
    public String user() {
        return "USER ACCESS";
    }

    @GetMapping("/api/admin/test")
    public String admin() {
        return "ADMIN ACCESS";
    }
}
