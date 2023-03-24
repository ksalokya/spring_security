package com.erp.auth.controller;

import com.erp.auth.entity.UserInfo;
import com.erp.auth.repository.UserInfoRepository;
import com.erp.auth.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/v1")
public class AuthController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/health")
    public String healthStatus() {
        return "Up!";
    }

    @GetMapping("/user/message")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String secretMessage() {
        return "You are authorized as a user!";
    }

    @GetMapping("/admin/message")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminSecretMessage() {
        return "You are authorized as a admin!";
    }

    @PostMapping("/new")
    public ResponseEntity<String> addUser(@RequestBody UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfoRepository.save(userInfo);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}
