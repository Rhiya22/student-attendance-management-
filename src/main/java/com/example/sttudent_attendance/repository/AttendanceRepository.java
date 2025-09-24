package com.example.sttudent_attendance.repository;

import com.example.sttudent_attendance.entity.Attendance;
import com.example.sttudent_attendance.entity.Course;
import com.example.sttudent_attendance.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    
    List<Attendance> findByCourseAndDateBetween(Course course, LocalDate startDate, LocalDate endDate);
    
    List<Attendance> findByStudentAndDateBetween(Student student, LocalDate startDate, LocalDate endDate);
    
    Optional<Attendance> findByStudentAndCourseAndDate(Student student, Course course, LocalDate date);
    
    @Query("SELECT a FROM Attendance a WHERE a.course = :course AND a.date BETWEEN :startDate AND :endDate ORDER BY a.date DESC")
    List<Attendance> findAttendanceByCourseAndDateRange(@Param("course") Course course, 
                                                       @Param("startDate") LocalDate startDate, 
                                                       @Param("endDate") LocalDate endDate);
    
    @Query("SELECT a FROM Attendance a WHERE a.student = :student AND a.date BETWEEN :startDate AND :endDate ORDER BY a.date DESC")
    List<Attendance> findAttendanceByStudentAndDateRange(@Param("student") Student student, 
                                                         @Param("startDate") LocalDate startDate, 
                                                         @Param("endDate") LocalDate endDate);
}

