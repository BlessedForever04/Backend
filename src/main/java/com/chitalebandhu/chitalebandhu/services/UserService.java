package com.chitalebandhu.chitalebandhu.services;

import com.chitalebandhu.chitalebandhu.entity.User;
import com.chitalebandhu.chitalebandhu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public void updatePassword(String oldPassword, String newPassword){
        newPassword = passwordEncoder.encode(newPassword);
        Optional<User> existingUser = userRepository.findByPassword(oldPassword);
        if(existingUser.isPresent()){
            existingUser.get().setPassword(newPassword);
        }
        else{
            throw new RuntimeException("User is not found");
        }
    }
}

