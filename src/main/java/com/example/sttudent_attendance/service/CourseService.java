package com.example.sttudent_attendance.service;

import com.example.sttudent_attendance.entity.Course;
import com.example.sttudent_attendance.entity.Student;
import com.example.sttudent_attendance.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    
    private final CourseRepository courseRepository;
    
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }
    
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }
    
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
    
    public void enrollStudentInCourse(Long courseId, Long studentId, StudentService studentService) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        
        Student student = studentService.getStudentById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        if (!course.getStudents().contains(student)) {
            course.addStudent(student);
            courseRepository.save(course);
        }
    }
    
    public void removeStudentFromCourse(Long courseId, Long studentId, StudentService studentService) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        
        Student student = studentService.getStudentById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        if (course.getStudents().contains(student)) {
            course.removeStudent(student);
            courseRepository.save(course);
        }
    }
}
