package com.example.sttudent_attendance.controller;

import com.example.sttudent_attendance.entity.User;
import com.example.sttudent_attendance.service.StudentService;
import com.example.sttudent_attendance.service.CourseService;
import com.example.sttudent_attendance.service.AttendanceService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    
    private final StudentService studentService;
    private final CourseService courseService;
    private final AttendanceService attendanceService;
    
    public DashboardController(StudentService studentService, CourseService courseService, AttendanceService attendanceService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.attendanceService = attendanceService;
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        
        // Add statistics based on user role
        switch (user.getRole()) {
            case ADMIN:
                model.addAttribute("totalStudents", studentService.getAllStudents().size());
                model.addAttribute("totalCourses", courseService.getAllCourses().size());
                model.addAttribute("totalAttendances", attendanceService.getAllAttendances().size());
                break;
            case TEACHER:
                model.addAttribute("totalCourses", courseService.getAllCourses().size());
                model.addAttribute("totalStudents", studentService.getAllStudents().size());
                break;
            case STUDENT:
                // For students, we might want to show their attendance records
                break;
        }
        
        return "dashboard";
    }
}
