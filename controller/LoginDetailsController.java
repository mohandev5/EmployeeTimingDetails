package com.task.employeeSystem.controller;

import com.task.employeeSystem.entity.LoginDetails;
import com.task.employeeSystem.service.LoginDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoginDetailsController {
    @Autowired
   private LoginDetailsService loginDetailsService;

//    @PreAuthorize(("hasAuthority('ADMIN')"))
//    @GetMapping("/hai")
//    public String hai(){
//       return "Hai..!";
//   }
//
//    @GetMapping("/hello")
//    public String hello(){
//        return "hello..!";
//    }
//
//    @PreAuthorize(("hasAuthority('USER')"))
//    @GetMapping("/by")
//    public String by(){
//        return "by..!";
//    }

}
