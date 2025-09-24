# 🎓 Student Attendance Management System

A comprehensive Spring Boot web application for managing student attendance with role-based access control.

## 🚀 Features

- **Role-based Authentication**: Admin, Teacher, and Student roles
- **Custom Login System**: Students/Teachers login with ID and DOB
- **Attendance Management**: Mark individual or bulk attendance
- **Course Management**: Create and manage courses
- **Student Enrollment**: Enroll students in multiple courses
- **Teacher Management**: Add and manage teachers
- **Data Visualization**: Interactive charts and reports
- **Responsive Design**: Mobile-friendly Bootstrap UI

## 🛠️ Technology Stack

- **Backend**: Spring Boot 3.5.5, Spring Security, Spring Data JPA
- **Database**: H2 (In-memory)
- **Frontend**: Thymeleaf, Bootstrap 5, Chart.js
- **Build Tool**: Maven

## 🔑 Login Credentials

### Admin Access
- **Username**: `admin`
- **Password**: `admin123`

### Student Access (10 students)
| Student ID | Password (DOB) | Name |
|------------|----------------|------|
| `101` | `2000-05-15` | John Doe |
| `102` | `2001-08-22` | Jane Smith |
| `103` | `1999-12-03` | Mike Johnson |
| `104` | `2000-03-18` | Sarah Wilson |
| `105` | `2001-07-09` | David Brown |
| `106` | `2000-11-25` | Emma Davis |
| `107` | `1999-04-12` | Alex Garcia |
| `108` | `2001-09-07` | Lisa Martinez |
| `109` | `2000-01-30` | Ryan Taylor |
| `110` | `2001-06-14` | Olivia Anderson |

### Teacher Access (5 teachers)
| Teacher ID | Password (DOB) | Name |
|------------|----------------|------|
| `201` | `1975-03-10` | Prof. Williams |
| `202` | `1980-07-25` | Dr. Jones |
| `203` | `1978-11-08` | Ms. Thompson |
| `204` | `1982-02-14` | Mr. Clark |
| `205` | `1976-09-30` | Prof. Rodriguez |

## 🏃‍♂️ Quick Start

### Prerequisites
- Java 21+
- Maven 3.6+

### Running the Application

1. **Clone the repository**
   ```bash
   git clone <your-repo-url>
   cd student-attendance-management
   ```

2. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

3. **Access the application**
   - **Main URL**: http://localhost:8080
   - **H2 Console**: http://localhost:8080/h2-console
   - **JDBC URL**: `jdbc:h2:mem:testdb`
   - **Username**: `sa`
   - **Password**: `password`

## 📊 Features Overview

### Admin Capabilities
- ✅ Student Management (CRUD operations)
- ✅ Teacher Management (CRUD operations)
- ✅ Course Management (CRUD operations)
- ✅ Student Enrollment in Courses
- ✅ System-wide Reports

### Teacher Capabilities
- ✅ Mark Individual Attendance
- ✅ Bulk Attendance for Courses
- ✅ Generate Reports with Charts
- ✅ View Attendance Statistics

### Student Capabilities
- ✅ View Personal Attendance Reports
- ✅ Attendance Statistics
- ✅ Data Visualization Charts

## 🗄️ Database Schema

The application uses the following entities:
- **User**: Authentication and roles
- **Student**: Student information with unique IDs
- **Teacher**: Teacher information with unique IDs
- **Course**: Course details
- **Attendance**: Attendance records with status tracking

## 🎨 UI Features

- **Responsive Design**: Bootstrap 5 with mobile support
- **Interactive Charts**: Chart.js for data visualization
- **Modern UI**: Clean, professional interface
- **Role-based Navigation**: Different menus for different roles
- **Real-time Statistics**: Live attendance calculations

## 🔒 Security

- **Spring Security**: Role-based access control
- **Custom Authentication**: Multiple login methods
- **Session Management**: Secure login/logout
- **CSRF Protection**: Configured for web security

## 📈 Performance

- **JPA Optimization**: Proper lazy/eager loading
- **Transaction Management**: Data consistency
- **Query Optimization**: Custom repository methods

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📝 License

This project is licensed under the MIT License.

## 👨‍💻 Author

Created with ❤️ for educational purposes.

---

**Happy Coding! 🚀**