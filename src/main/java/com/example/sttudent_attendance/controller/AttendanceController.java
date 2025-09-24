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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class AttendanceController {
    
    private final AttendanceService attendanceService;
    private final CourseService courseService;
    private final StudentService studentService;
    
    public AttendanceController(AttendanceService attendanceService, CourseService courseService, StudentService studentService) {
        this.attendanceService = attendanceService;
        this.courseService = courseService;
        this.studentService = studentService;
    }
    
    @GetMapping("/attendance")
    public String attendanceForm(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("attendanceStatuses", Attendance.AttendanceStatus.values());
        return "attendance";
    }
    
    @PostMapping("/attendance")
    public String markAttendance(@RequestParam Long studentId,
                               @RequestParam Long courseId,
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                               @RequestParam Attendance.AttendanceStatus status,
                               RedirectAttributes redirectAttributes) {
        try {
            Student student = studentService.getStudentById(studentId)
                    .orElseThrow(() -> new RuntimeException("Student not found"));
            Course course = courseService.getCourseById(courseId)
                    .orElseThrow(() -> new RuntimeException("Course not found"));
            
            attendanceService.markAttendance(student, course, date, status);
            redirectAttributes.addFlashAttribute("success", "Attendance marked successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error marking attendance: " + e.getMessage());
        }
        return "redirect:/teacher/attendance";
    }
    
    @GetMapping("/attendance/course/{courseId}")
    public String attendanceByCourse(@PathVariable Long courseId, Model model) {
        Course course = courseService.getCourseById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        
        model.addAttribute("course", course);
        model.addAttribute("students", course.getStudents());
        model.addAttribute("attendanceStatuses", Attendance.AttendanceStatus.values());
        return "attendance-course";
    }
    
    @PostMapping("/attendance/course/{courseId}")
    public String markAttendanceForCourse(@PathVariable Long courseId,
                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                        @RequestParam List<Long> studentIds,
                                        @RequestParam List<Attendance.AttendanceStatus> statuses,
                                        RedirectAttributes redirectAttributes) {
        try {
            Course course = courseService.getCourseById(courseId)
                    .orElseThrow(() -> new RuntimeException("Course not found"));
            
            for (int i = 0; i < studentIds.size(); i++) {
                Student student = studentService.getStudentById(studentIds.get(i))
                        .orElseThrow(() -> new RuntimeException("Student not found"));
                attendanceService.markAttendance(student, course, date, statuses.get(i));
            }
            
            redirectAttributes.addFlashAttribute("success", "Attendance marked for all students!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error marking attendance: " + e.getMessage());
        }
        return "redirect:/teacher/attendance/course/" + courseId;
    }
}

