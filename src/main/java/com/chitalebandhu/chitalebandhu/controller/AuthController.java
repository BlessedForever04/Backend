package com.chitalebandhu.chitalebandhu.controller;

import com.chitalebandhu.chitalebandhu.DTOs.AuthRequest;
import com.chitalebandhu.chitalebandhu.Utility.JwtUtil;
import com.chitalebandhu.chitalebandhu.entity.User;
import com.chitalebandhu.chitalebandhu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request){
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getUserName(),
                    request.getPassword()
            )
        );
        return jwtUtil.generateToken(request.getUserName());
    }
}