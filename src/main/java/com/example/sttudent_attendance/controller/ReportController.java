package com.example.sttudent_attendance.controller;

import com.example.sttudent_attendance.entity.Attendance;
import com.example.sttudent_attendance.entity.Course;
import com.example.sttudent_attendance.entity.Student;
import com.example.sttudent_attendance.service.AttendanceService;
import com.example.sttudent_attendance.service.CourseService;
import com.example.sttudent_attendance.service.StudentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class ReportController {
    
    private final AttendanceService attendanceService;
    private final CourseService courseService;
    private final StudentService studentService;
    
    public ReportController(AttendanceService attendanceService, CourseService courseService, StudentService studentService) {
        this.attendanceService = attendanceService;
        this.courseService = courseService;
        this.studentService = studentService;
    }
    
    @GetMapping("/reports")
    public String reports(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("students", studentService.getAllStudents());
        return "reports";
    }
    
    @GetMapping("/reports/course")
    public String attendanceByCourseReport(@RequestParam Long courseId,
                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                         Model model) {
        Course course = courseService.getCourseById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        
        List<Attendance> attendances = attendanceService.getAttendanceByCourseAndDateRange(course, startDate, endDate);
        
        model.addAttribute("course", course);
        model.addAttribute("attendances", attendances);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("students", studentService.getAllStudents());
        
        return "reports";
    }
    
    @GetMapping("/reports/student")
    public String attendanceByStudentReport(@RequestParam Long studentId,
                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                          Model model) {
        Student student = studentService.getStudentById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        List<Attendance> attendances = attendanceService.getAttendanceByStudentAndDateRange(student, startDate, endDate);
        
        model.addAttribute("student", student);
        model.addAttribute("attendances", attendances);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("students", studentService.getAllStudents());
        
        return "reports";
    }
}

