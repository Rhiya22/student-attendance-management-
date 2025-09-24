package com.example.sttudent_attendance.controller;

import com.example.sttudent_attendance.entity.Student;
import com.example.sttudent_attendance.entity.Course;
import com.example.sttudent_attendance.service.StudentService;
import com.example.sttudent_attendance.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin")
public class StudentController {
    
    private final StudentService studentService;
    private final CourseService courseService;
    
    public StudentController(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }
    
    @GetMapping("/students")
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("student", new Student());
        model.addAttribute("courses", courseService.getAllCourses());
        return "students";
    }
    
    @PostMapping("/students")
    public String addStudent(@ModelAttribute Student student, 
                           @RequestParam(required = false) Long[] courseIds,
                           RedirectAttributes redirectAttributes) {
        try {
            if (studentService.existsByStudentId(student.getStudentId())) {
                redirectAttributes.addFlashAttribute("error", "Student with this ID already exists!");
                return "redirect:/admin/students";
            }
            if (studentService.existsByEmail(student.getEmail())) {
                redirectAttributes.addFlashAttribute("error", "Student with this email already exists!");
                return "redirect:/admin/students";
            }
            
            // Save the student first
            studentService.saveStudent(student);
            
            // Enroll in selected courses
            if (courseIds != null && courseIds.length > 0) {
                for (Long courseId : courseIds) {
                    Course course = courseService.getCourseById(courseId)
                            .orElseThrow(() -> new RuntimeException("Course not found"));
                    courseService.enrollStudentInCourse(courseId, student.getId(), studentService);
                }
            }
            
            redirectAttributes.addFlashAttribute("success", "Student added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error adding student: " + e.getMessage());
        }
        return "redirect:/admin/students";
    }
    
    @PostMapping("/students/{id}/delete")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            studentService.deleteStudent(id);
            redirectAttributes.addFlashAttribute("success", "Student deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting student: " + e.getMessage());
        }
        return "redirect:/admin/students";
    }
    
    @GetMapping("/students/{id}/edit")
    public String editStudentForm(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        model.addAttribute("student", student);
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("courses", courseService.getAllCourses());
        return "students";
    }
    
    @PostMapping("/students/{id}/edit")
    public String updateStudent(@PathVariable Long id, 
                              @ModelAttribute Student student,
                              @RequestParam(required = false) Long[] courseIds,
                              RedirectAttributes redirectAttributes) {
        try {
            Student existingStudent = studentService.getStudentById(id)
                    .orElseThrow(() -> new RuntimeException("Student not found"));
            
            // Check if student ID is being changed and if it already exists
            if (!existingStudent.getStudentId().equals(student.getStudentId()) && 
                studentService.existsByStudentId(student.getStudentId())) {
                redirectAttributes.addFlashAttribute("error", "Student with this ID already exists!");
                return "redirect:/admin/students";
            }
            
            // Check if email is being changed and if it already exists
            if (!existingStudent.getEmail().equals(student.getEmail()) && 
                studentService.existsByEmail(student.getEmail())) {
                redirectAttributes.addFlashAttribute("error", "Student with this email already exists!");
                return "redirect:/admin/students";
            }
            
            existingStudent.setStudentId(student.getStudentId());
            existingStudent.setName(student.getName());
            existingStudent.setEmail(student.getEmail());
            existingStudent.setDateOfBirth(student.getDateOfBirth());
            studentService.saveStudent(existingStudent);
            
            redirectAttributes.addFlashAttribute("success", "Student updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating student: " + e.getMessage());
        }
        return "redirect:/admin/students";
    }
}
