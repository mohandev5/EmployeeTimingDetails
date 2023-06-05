package com.task.employeeSystem.service;

import com.task.employeeSystem.entity.Employee;
import  com.task.employeeSystem.entity.LoginDetails;
import com.task.employeeSystem.repo.EmployeeRepo;
import com.task.employeeSystem.exception.UserNameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepo employeeRepo;

    public Page<Employee> getAllDetails(int pageNumber, int pageSize, String sortAttribute) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortAttribute));
        return employeeRepo.findAll(pageable);
    }

    public List<Employee> getDetailsByName(String employeeName) throws UserNameNotFoundException{
        var nameDetails = employeeRepo.findEmployeeByEmployeeName(employeeName);
        if (nameDetails.isEmpty()) {
            throw new UserNameNotFoundException("user not found with the given name:"+ employeeName);
        } else {
            return  nameDetails;
        }
    }

    public List<Duration> getAllWorkingHours(String employeeName) {
        List<Employee> employees = employeeRepo.findEmployeeByEmployeeName(employeeName);
        List<Duration> workingHoursList = new ArrayList<>();
        Duration duration = null;
        Duration totalDuration = Duration.ZERO;
        for (Employee employee : employees) {
            for (LoginDetails loginDetails : employee.getLoginDetails()) {
                LocalTime loginTime = loginDetails.getLogin().toLocalTime();
                LocalTime logoutTime = loginDetails.getLogout().toLocalTime();
                duration = Duration.between(logoutTime, loginTime);
                workingHoursList.add(duration);
            }
        }
        for(int i=0;i<workingHoursList.size();i++){
            totalDuration = totalDuration.plus(duration);
        }
        return Collections.singletonList((totalDuration));
    }

    public double aggregatePercentageOfWorking(String employeeName) throws UserNameNotFoundException {
        List<Employee> employees = employeeRepo.findEmployeeByEmployeeName(employeeName);
        List<Duration> workingHours = new ArrayList<>();
        Duration totalDuration = Duration.ZERO;
        int weeklyWorkingHours = 40;
        double aggregateWorkingHours = 0;
        if (employees.isEmpty()) {
            throw new UserNameNotFoundException("user not found with the given name:" + employeeName);
        } else {
            for (Employee employee : employees) {
                for (LoginDetails loginDetails : employee.getLoginDetails()) {
                    LocalTime loginTime = loginDetails.getLogin().toLocalTime();
                    LocalTime logoutTime = loginDetails.getLogout().toLocalTime();
                    Duration duration = Duration.between(loginTime, logoutTime);
                        totalDuration = totalDuration.plus(duration);
                }
            }
            aggregateWorkingHours = (totalDuration.toHours() / (double) weeklyWorkingHours) * 100;
            return aggregateWorkingHours;
        }

    }

    public double numberofLeaves(String employeeName) throws UserNameNotFoundException {
        List<Employee> employees = employeeRepo.findEmployeeByEmployeeName(employeeName);
        List<Duration> workingHours = new ArrayList<>();
        Duration totalDuration = Duration.ZERO;
        double totalNumberOfWorkingDaysInWeek = 5;
        double day = 0, numberofleaves = 0,halfday=0;
        if (employees.isEmpty()) {
            throw new UserNameNotFoundException("user not found with the given name: " + employeeName);
        } else {
            for (Employee employee : employees) {
                for (LoginDetails loginDetails : employee.getLoginDetails()) {
                    LocalTime loginTime = loginDetails.getLogin().toLocalTime();
                    LocalTime logoutTime = loginDetails.getLogout().toLocalTime();
                    Duration duration = Duration.between(loginTime, logoutTime);
                    if (duration.toHours() == 4) {
                      halfday++;
                    }
                    else if(duration.toHours()>4){
                        day++;
                    }
                }
            }
            numberofleaves = totalNumberOfWorkingDaysInWeek-(halfday/2+day);
        }
        return numberofleaves;
    }

}


