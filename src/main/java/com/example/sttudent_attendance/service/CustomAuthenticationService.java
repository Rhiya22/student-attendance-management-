package com.example.sttudent_attendance.service;

import com.example.sttudent_attendance.entity.User;
import com.example.sttudent_attendance.entity.Student;
import com.example.sttudent_attendance.entity.Teacher;
import com.example.sttudent_attendance.repository.UserRepository;
import com.example.sttudent_attendance.repository.StudentRepository;
import com.example.sttudent_attendance.repository.TeacherRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomAuthenticationService implements AuthenticationProvider {
    
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    
    public CustomAuthenticationService(UserRepository userRepository, 
                                     StudentRepository studentRepository,
                                     TeacherRepository teacherRepository) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        // Try to authenticate as student first
        Student student = studentRepository.findByStudentId(username).orElse(null);
        if (student != null) {
            String dobString = student.getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            if (password.equals(dobString)) {
                Collection<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
                return new UsernamePasswordAuthenticationToken(student, password, authorities);
            }
        }
        
        // Try to authenticate as teacher
        Teacher teacher = teacherRepository.findByTeacherId(username).orElse(null);
        if (teacher != null) {
            String dobString = teacher.getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            if (password.equals(dobString)) {
                Collection<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_TEACHER"));
                return new UsernamePasswordAuthenticationToken(teacher, password, authorities);
            }
        }
        
        // Try to authenticate as regular user (admin)
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null && password.equals(user.getPassword())) {
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
            return new UsernamePasswordAuthenticationToken(user, password, authorities);
        }
        
        throw new BadCredentialsException("Invalid username or password");
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

