package com.example.sttudent_attendance.controller;

import com.example.sttudent_attendance.entity.Attendance;
import com.example.sttudent_attendance.entity.User;
import com.example.sttudent_attendance.service.AttendanceService;
import com.example.sttudent_attendance.service.StudentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentReportController {
    
    private final AttendanceService attendanceService;
    private final StudentService studentService;
    
    public StudentReportController(AttendanceService attendanceService, StudentService studentService) {
        this.attendanceService = attendanceService;
        this.studentService = studentService;
    }
    
    @GetMapping("/reports")
    public String studentReports(Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        return "student-reports";
    }
    
    @GetMapping("/reports/my-attendance")
    public String myAttendanceReport(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                   @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                   Authentication authentication,
                                   Model model) {
        User user = (User) authentication.getPrincipal();
        
        // For demo purposes, we'll find a student by username
        // In a real application, you'd have a proper relationship between User and Student
        var student = studentService.getAllStudents().stream()
                .filter(s -> s.getName().toLowerCase().contains(user.getUsername().toLowerCase()))
                .findFirst()
                .orElse(null);
        
        if (student == null) {
            model.addAttribute("error", "Student profile not found. Please contact administrator.");
            return "student-reports";
        }
        
        // Set default date range if not provided
        if (startDate == null) {
            startDate = LocalDate.now().minusDays(30);
        }
        if (endDate == null) {
            endDate = LocalDate.now();
        }
        
        List<Attendance> attendances = attendanceService.getAttendanceByStudentAndDateRange(student, startDate, endDate);
        
        model.addAttribute("student", student);
        model.addAttribute("attendances", attendances);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("user", user);
        
        return "student-reports";
    }
}

