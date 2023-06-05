package com.task.employeeSystem.service;

import com.task.employeeSystem.entity.User;
import com.task.employeeSystem.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    public String addnewUser(User user){
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        userRepo.save(user);
        return "user created successfully";
    }
}
