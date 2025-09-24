package com.example.sttudent_attendance.service;

import com.example.sttudent_attendance.entity.Teacher;
import com.example.sttudent_attendance.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {
    
    private final TeacherRepository teacherRepository;
    
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }
    
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }
    
    public Optional<Teacher> getTeacherById(Long id) {
        return teacherRepository.findById(id);
    }
    
    public Optional<Teacher> getTeacherByTeacherId(String teacherId) {
        return teacherRepository.findByTeacherId(teacherId);
    }
    
    public Optional<Teacher> getTeacherByEmail(String email) {
        return teacherRepository.findByEmail(email);
    }
    
    public Teacher saveTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }
    
    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }
    
    public boolean existsByTeacherId(String teacherId) {
        return teacherRepository.existsByTeacherId(teacherId);
    }
    
    public boolean existsByEmail(String email) {
        return teacherRepository.existsByEmail(email);
    }
}

