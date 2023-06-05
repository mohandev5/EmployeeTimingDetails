package com.task.employeeSystem.security;

import com.task.employeeSystem.entity.User;
import com.task.employeeSystem.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class SecurityDetails implements UserDetailsService {
    @Autowired
    UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<User> user= userRepo.findByName(name);
        return user.map(com.task.employeeSystem.security.UserDetails::new).orElseThrow();
    }
}
