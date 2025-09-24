package com.example.sttudent_attendance.service;

import com.example.sttudent_attendance.entity.Attendance;
import com.example.sttudent_attendance.entity.Course;
import com.example.sttudent_attendance.entity.Student;
import com.example.sttudent_attendance.repository.AttendanceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {
    
    private final AttendanceRepository attendanceRepository;
    
    public AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }
    
    public List<Attendance> getAllAttendances() {
        return attendanceRepository.findAll();
    }
    
    public Optional<Attendance> getAttendanceById(Long id) {
        return attendanceRepository.findById(id);
    }
    
    public Attendance saveAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }
    
    public void deleteAttendance(Long id) {
        attendanceRepository.deleteById(id);
    }
    
    public List<Attendance> getAttendanceByCourseAndDateRange(Course course, LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findAttendanceByCourseAndDateRange(course, startDate, endDate);
    }
    
    public List<Attendance> getAttendanceByStudentAndDateRange(Student student, LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findAttendanceByStudentAndDateRange(student, startDate, endDate);
    }
    
    public Optional<Attendance> getAttendanceByStudentCourseAndDate(Student student, Course course, LocalDate date) {
        return attendanceRepository.findByStudentAndCourseAndDate(student, course, date);
    }
    
    public Attendance markAttendance(Student student, Course course, LocalDate date, Attendance.AttendanceStatus status) {
        // Check if attendance already exists for this student, course, and date
        Optional<Attendance> existingAttendance = getAttendanceByStudentCourseAndDate(student, course, date);
        
        if (existingAttendance.isPresent()) {
            // Update existing attendance
            Attendance attendance = existingAttendance.get();
            attendance.setStatus(status);
            return saveAttendance(attendance);
        } else {
            // Create new attendance record
            Attendance attendance = new Attendance(student, course, date, status);
            return saveAttendance(attendance);
        }
    }
}

