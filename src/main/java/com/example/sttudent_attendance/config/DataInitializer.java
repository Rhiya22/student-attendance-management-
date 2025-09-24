package com.example.sttudent_attendance.config;

import com.example.sttudent_attendance.entity.User;
import com.example.sttudent_attendance.entity.Student;
import com.example.sttudent_attendance.entity.Teacher;
import com.example.sttudent_attendance.entity.Course;
import com.example.sttudent_attendance.entity.Attendance;
import com.example.sttudent_attendance.repository.UserRepository;
import com.example.sttudent_attendance.repository.StudentRepository;
import com.example.sttudent_attendance.repository.TeacherRepository;
import com.example.sttudent_attendance.repository.CourseRepository;
import com.example.sttudent_attendance.repository.AttendanceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final AttendanceRepository attendanceRepository;
    private final PasswordEncoder passwordEncoder;
    
    public DataInitializer(UserRepository userRepository, StudentRepository studentRepository, 
                          TeacherRepository teacherRepository, CourseRepository courseRepository, 
                          AttendanceRepository attendanceRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
        this.attendanceRepository = attendanceRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Create admin user if it doesn't exist
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin123");
            admin.setRole(User.Role.ADMIN);
            userRepository.save(admin);
            System.out.println("Admin user created successfully!");
        }
        
        // Create sample courses
        createSampleCourses();
        
        // Create sample students and teachers
        createSampleStudents();
        createSampleTeachers();
        
        // Create sample attendance records
        createSampleAttendance();
        
        System.out.println("Sample data initialization completed!");
    }
    
    private void createSampleCourses() {
        List<Course> courses = Arrays.asList(
            new Course("Mathematics", "Advanced mathematics course covering algebra, calculus, and statistics"),
            new Course("Physics", "Fundamental physics principles and laboratory work"),
            new Course("Chemistry", "Organic and inorganic chemistry with practical experiments"),
            new Course("Biology", "Cell biology, genetics, and evolution"),
            new Course("Computer Science", "Programming, algorithms, and software development"),
            new Course("English Literature", "Classic and modern literature analysis"),
            new Course("History", "World history from ancient civilizations to modern times"),
            new Course("Art", "Drawing, painting, and art history")
        );
        
        for (Course course : courses) {
            if (!courseRepository.findAll().stream().anyMatch(c -> c.getName().equals(course.getName()))) {
                courseRepository.save(course);
            }
        }
    }
    
    private void createSampleStudents() {
        List<Object[]> studentData = Arrays.asList(
            new Object[]{"101", "John Doe", "john.doe@university.edu", LocalDate.of(2000, 5, 15)},
            new Object[]{"102", "Jane Smith", "jane.smith@university.edu", LocalDate.of(2001, 8, 22)},
            new Object[]{"103", "Mike Johnson", "mike.johnson@university.edu", LocalDate.of(1999, 12, 3)},
            new Object[]{"104", "Sarah Wilson", "sarah.wilson@university.edu", LocalDate.of(2000, 3, 18)},
            new Object[]{"105", "David Brown", "david.brown@university.edu", LocalDate.of(2001, 7, 9)},
            new Object[]{"106", "Emma Davis", "emma.davis@university.edu", LocalDate.of(2000, 11, 25)},
            new Object[]{"107", "Alex Garcia", "alex.garcia@university.edu", LocalDate.of(1999, 4, 12)},
            new Object[]{"108", "Lisa Martinez", "lisa.martinez@university.edu", LocalDate.of(2001, 9, 7)},
            new Object[]{"109", "Ryan Taylor", "ryan.taylor@university.edu", LocalDate.of(2000, 1, 30)},
            new Object[]{"110", "Olivia Anderson", "olivia.anderson@university.edu", LocalDate.of(2001, 6, 14)}
        );
        
        List<Course> courses = courseRepository.findAll();
        
        for (Object[] data : studentData) {
            String studentId = (String) data[0];
            String name = (String) data[1];
            String email = (String) data[2];
            LocalDate dateOfBirth = (LocalDate) data[3];
            
            // Create student profile
            if (!studentRepository.existsByStudentId(studentId)) {
                Student student = new Student(studentId, name, email, dateOfBirth);
                studentRepository.save(student);
                
                // Enroll in random courses by updating the course entities
                int numCourses = (int) (Math.random() * 4) + 2; // 2-5 courses
                for (int i = 0; i < numCourses; i++) {
                    Course randomCourse = courses.get((int) (Math.random() * courses.size()));
                    // Add student to course instead of adding course to student
                    randomCourse.addStudent(student);
                    courseRepository.save(randomCourse);
                }
            }
        }
    }
    
    private void createSampleTeachers() {
        List<Object[]> teacherData = Arrays.asList(
            new Object[]{"201", "Prof. Williams", "williams@university.edu", LocalDate.of(1975, 3, 10), "Mathematics", "Calculus"},
            new Object[]{"202", "Dr. Jones", "jones@university.edu", LocalDate.of(1980, 7, 25), "Physics", "Quantum Mechanics"},
            new Object[]{"203", "Ms. Thompson", "thompson@university.edu", LocalDate.of(1978, 11, 8), "Chemistry", "Organic Chemistry"},
            new Object[]{"204", "Mr. Clark", "clark@university.edu", LocalDate.of(1982, 2, 14), "Biology", "Genetics"},
            new Object[]{"205", "Prof. Rodriguez", "rodriguez@university.edu", LocalDate.of(1976, 9, 30), "Computer Science", "Software Engineering"}
        );
        
        for (Object[] data : teacherData) {
            String teacherId = (String) data[0];
            String name = (String) data[1];
            String email = (String) data[2];
            LocalDate dateOfBirth = (LocalDate) data[3];
            String department = (String) data[4];
            String specialization = (String) data[5];
            
            if (!teacherRepository.existsByTeacherId(teacherId)) {
                Teacher teacher = new Teacher(teacherId, name, email, dateOfBirth, department, specialization);
                teacherRepository.save(teacher);
            }
        }
    }
    
    private void createSampleAttendance() {
        List<Student> students = studentRepository.findAll();
        List<Course> courses = courseRepository.findAll();
        
        // Create attendance records for the last 30 days
        for (int i = 0; i < 30; i++) {
            LocalDate date = LocalDate.now().minusDays(i);
            
            // Create some sample attendance records
            for (Student student : students) {
                // Each student attends 2-3 random courses per day
                int coursesPerDay = (int) (Math.random() * 2) + 2; // 2-3 courses
                
                for (int j = 0; j < coursesPerDay; j++) {
                    Course randomCourse = courses.get((int) (Math.random() * courses.size()));
                    
                    // Randomly decide if attendance was recorded (80% chance)
                    if (Math.random() < 0.8) {
                        Attendance.AttendanceStatus status;
                        double rand = Math.random();
                        if (rand < 0.7) {
                            status = Attendance.AttendanceStatus.PRESENT;
                        } else if (rand < 0.9) {
                            status = Attendance.AttendanceStatus.LATE;
                        } else {
                            status = Attendance.AttendanceStatus.ABSENT;
                        }
                        
                        Attendance attendance = new Attendance(student, randomCourse, date, status);
                        attendanceRepository.save(attendance);
                    }
                }
            }
        }
    }
}
