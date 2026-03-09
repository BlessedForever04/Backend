package com.chitalebandhu.chitalebandhu.controller;

import com.chitalebandhu.chitalebandhu.DTOs.LoginRequest;
import com.chitalebandhu.chitalebandhu.entity.User;
import com.chitalebandhu.chitalebandhu.repository.UserRepository;
import com.chitalebandhu.chitalebandhu.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public User register(@RequestBody User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(user.getUsername());
    }
}