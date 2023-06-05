package com.task.employeeSystem.controller;

import com.task.employeeSystem.entity.User;
import com.task.employeeSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping ("/new")
    public String addNewUser(@RequestBody User user){
        return userService.addnewUser(user);
    }
}
