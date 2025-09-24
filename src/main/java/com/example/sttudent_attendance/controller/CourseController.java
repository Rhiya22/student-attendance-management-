package com.example.sttudent_attendance.controller;

import com.example.sttudent_attendance.entity.Course;
import com.example.sttudent_attendance.service.CourseService;
import com.example.sttudent_attendance.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class CourseController {
    
    private final CourseService courseService;
    private final StudentService studentService;
    
    public CourseController(CourseService courseService, StudentService studentService) {
        this.courseService = courseService;
        this.studentService = studentService;
    }
    
    @GetMapping("/courses")
    public String listCourses(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("course", new Course());
        model.addAttribute("students", studentService.getAllStudents());
        return "courses";
    }
    
    @PostMapping("/courses")
    public String addCourse(@ModelAttribute Course course, RedirectAttributes redirectAttributes) {
        try {
            courseService.saveCourse(course);
            redirectAttributes.addFlashAttribute("success", "Course added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error adding course: " + e.getMessage());
        }
        return "redirect:/admin/courses";
    }
    
    @PostMapping("/courses/{id}/delete")
    public String deleteCourse(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            courseService.deleteCourse(id);
            redirectAttributes.addFlashAttribute("success", "Course deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting course: " + e.getMessage());
        }
        return "redirect:/admin/courses";
    }
    
    @GetMapping("/courses/{id}/edit")
    public String editCourseForm(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        model.addAttribute("course", course);
        model.addAttribute("courses", courseService.getAllCourses());
        return "courses";
    }
    
    @PostMapping("/courses/{id}/edit")
    public String updateCourse(@PathVariable Long id, @ModelAttribute Course course, RedirectAttributes redirectAttributes) {
        try {
            Course existingCourse = courseService.getCourseById(id)
                    .orElseThrow(() -> new RuntimeException("Course not found"));
            
            existingCourse.setName(course.getName());
            existingCourse.setDescription(course.getDescription());
            courseService.saveCourse(existingCourse);
            redirectAttributes.addFlashAttribute("success", "Course updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating course: " + e.getMessage());
        }
        return "redirect:/admin/courses";
    }
}
