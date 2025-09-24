package com.example.sttudent_attendance.controller;

import com.example.sttudent_attendance.entity.Teacher;
import com.example.sttudent_attendance.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class TeacherController {
    
    private final TeacherService teacherService;
    
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }
    
    @GetMapping("/teachers")
    public String listTeachers(Model model) {
        model.addAttribute("teachers", teacherService.getAllTeachers());
        model.addAttribute("teacher", new Teacher());
        return "teachers";
    }
    
    @PostMapping("/teachers")
    public String addTeacher(@ModelAttribute Teacher teacher, RedirectAttributes redirectAttributes) {
        try {
            if (teacherService.existsByTeacherId(teacher.getTeacherId())) {
                redirectAttributes.addFlashAttribute("error", "Teacher with this ID already exists!");
                return "redirect:/admin/teachers";
            }
            if (teacherService.existsByEmail(teacher.getEmail())) {
                redirectAttributes.addFlashAttribute("error", "Teacher with this email already exists!");
                return "redirect:/admin/teachers";
            }
            teacherService.saveTeacher(teacher);
            redirectAttributes.addFlashAttribute("success", "Teacher added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error adding teacher: " + e.getMessage());
        }
        return "redirect:/admin/teachers";
    }
    
    @PostMapping("/teachers/{id}/delete")
    public String deleteTeacher(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            teacherService.deleteTeacher(id);
            redirectAttributes.addFlashAttribute("success", "Teacher deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting teacher: " + e.getMessage());
        }
        return "redirect:/admin/teachers";
    }
    
    @GetMapping("/teachers/{id}/edit")
    public String editTeacherForm(@PathVariable Long id, Model model) {
        Teacher teacher = teacherService.getTeacherById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        model.addAttribute("teacher", teacher);
        model.addAttribute("teachers", teacherService.getAllTeachers());
        return "teachers";
    }
    
    @PostMapping("/teachers/{id}/edit")
    public String updateTeacher(@PathVariable Long id, @ModelAttribute Teacher teacher, RedirectAttributes redirectAttributes) {
        try {
            Teacher existingTeacher = teacherService.getTeacherById(id)
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));
            
            // Check if teacher ID is being changed and if it already exists
            if (!existingTeacher.getTeacherId().equals(teacher.getTeacherId()) && 
                teacherService.existsByTeacherId(teacher.getTeacherId())) {
                redirectAttributes.addFlashAttribute("error", "Teacher with this ID already exists!");
                return "redirect:/admin/teachers";
            }
            
            // Check if email is being changed and if it already exists
            if (!existingTeacher.getEmail().equals(teacher.getEmail()) && 
                teacherService.existsByEmail(teacher.getEmail())) {
                redirectAttributes.addFlashAttribute("error", "Teacher with this email already exists!");
                return "redirect:/admin/teachers";
            }
            
            existingTeacher.setTeacherId(teacher.getTeacherId());
            existingTeacher.setName(teacher.getName());
            existingTeacher.setEmail(teacher.getEmail());
            existingTeacher.setDateOfBirth(teacher.getDateOfBirth());
            existingTeacher.setDepartment(teacher.getDepartment());
            existingTeacher.setSpecialization(teacher.getSpecialization());
            teacherService.saveTeacher(existingTeacher);
            redirectAttributes.addFlashAttribute("success", "Teacher updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating teacher: " + e.getMessage());
        }
        return "redirect:/admin/teachers";
    }
}

