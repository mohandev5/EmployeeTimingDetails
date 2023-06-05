package com.task.employeeSystem.controller;
import com.task.employeeSystem.dto.AuthRequests;
import com.task.employeeSystem.exception.UserNameNotFoundException;
import com.task.employeeSystem.service.EmployeeService;
import com.task.employeeSystem.entity.Employee;
import com.task.employeeSystem.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public EmployeeController() {
    }


    @GetMapping("/employees/{pageNumber}/{pageSize}/{sortAttribute}")
    public ResponseEntity<Page<Employee>> dispalyAll(@PathVariable("pageNumber") int pageNumber, @PathVariable("pageSize") int pageSize, @PathVariable("sortAttribute") String sortAttribute){
        return ResponseEntity.ok(employeeService.getAllDetails(pageNumber,pageSize,sortAttribute));
    }

    @GetMapping("/employeedetailsByEmployeeName/{employeeName}")
    public ResponseEntity<List<Employee>>displayemployeesByName(@PathVariable ("employeeName") String employeeName) throws UserNameNotFoundException {
        return  ResponseEntity.ok(employeeService.getDetailsByName(employeeName));
    }
    @PreAuthorize(("hasAuthority('ADMIN')"))
    @GetMapping("/countWorkinghours/{employeeName}")
    public ResponseEntity<String> calaculateworkinghours(@PathVariable ("employeeName") String employeeName){
        return ResponseEntity.ok(employeeName+" "+"working hours:"+employeeService.getAllWorkingHours(employeeName));
    }

    @PreAuthorize(("hasAuthority('ADMIN')"))
    @GetMapping("/averageWorkingHoursInAWeek/{employeeName}")
    public ResponseEntity<String> calaculateAverage(@PathVariable("employeeName")String employeeName) throws UserNameNotFoundException {
        return ResponseEntity.ok(employeeName+" "+"average working hours:"+employeeService.aggregatePercentageOfWorking(employeeName));
    }
    @PreAuthorize(("hasAuthority('ADMIN')"))
    @GetMapping("numberOfLeaves/{employeeName}")
    public ResponseEntity<String> leavesNumber(@PathVariable("employeeName")String employeeName) throws UserNameNotFoundException {
        return ResponseEntity.ok(employeeName+" "+"leaves:"+employeeService.numberofLeaves(employeeName));
    }

    @PostMapping("/authenticate")
    public String authenticateJwt(@RequestBody AuthRequests authRequests){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequests.getName(), authRequests.getPassword()));
        if(authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequests.getName());
        }
        else {
            throw new UsernameNotFoundException(("invalid username"));
        }
    }
}
