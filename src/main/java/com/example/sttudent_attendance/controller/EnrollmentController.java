package com.example.sttudent_attendance.controller;

import com.example.sttudent_attendance.service.CourseService;
import com.example.sttudent_attendance.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class EnrollmentController {
    
    private final CourseService courseService;
    private final StudentService studentService;
    
    public EnrollmentController(CourseService courseService, StudentService studentService) {
        this.courseService = courseService;
        this.studentService = studentService;
    }
    
    @PostMapping("/courses/{courseId}/enroll/{studentId}")
    public String enrollStudentInCourse(@PathVariable Long courseId, 
                                      @PathVariable Long studentId,
                                      RedirectAttributes redirectAttributes) {
        try {
            courseService.enrollStudentInCourse(courseId, studentId, studentService);
            redirectAttributes.addFlashAttribute("success", "Student enrolled successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error enrolling student: " + e.getMessage());
        }
        return "redirect:/admin/courses";
    }
    
    @PostMapping("/courses/{courseId}/unenroll/{studentId}")
    public String unenrollStudentFromCourse(@PathVariable Long courseId, 
                                          @PathVariable Long studentId,
                                          RedirectAttributes redirectAttributes) {
        try {
            courseService.removeStudentFromCourse(courseId, studentId, studentService);
            redirectAttributes.addFlashAttribute("success", "Student unenrolled successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error unenrolling student: " + e.getMessage());
        }
        return "redirect:/admin/courses";
    }
}

