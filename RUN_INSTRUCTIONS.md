# Student Attendance Management System - Run Instructions

## How to Run the Application

### Method 1: Using Maven (Recommended)
```bash
# Navigate to the project directory
cd sttudent-attendance

# Run the application
mvn spring-boot:run
```

### Method 2: Using the Batch File (Windows)
```bash
# Double-click run.bat or run from command prompt
run.bat
```

### Method 3: Using Maven Wrapper (if available)
```bash
# On Windows
mvnw.cmd spring-boot:run

# On Unix/Linux/Mac
./mvnw spring-boot:run
```

## Access the Application

Once the application starts successfully, you can access it at:
- **Main Application**: http://localhost:8080
- **H2 Database Console**: http://localhost:8080/h2-console

## Login Credentials

### Admin Access
- **Username**: `admin`
- **Password**: `admin123`
- **Access**: Full system management

### Teacher Access (5 teachers available)
- **Username**: `prof_williams`, `dr_jones`, `ms_thompson`, `mr_clark`, `prof_rodriguez`
- **Password**: `teacher123`
- **Access**: Mark attendance, generate reports

### Student Access (10 students available)
- **Username**: `john_doe`, `jane_smith`, `mike_johnson`, `sarah_wilson`, `david_brown`, `emma_davis`, `alex_garcia`, `lisa_martinez`, `ryan_taylor`, `olivia_anderson`
- **Password**: `student123`
- **Access**: View personal attendance reports

## Key Features to Test

### 1. Admin Features
- Login as `admin` / `admin123`
- Go to **Students** page → Add new students
- Go to **Courses** page → Add new courses
- Click **"Manage"** button next to courses → Enroll students
- View dashboard with statistics

### 2. Teacher Features
- Login as `prof_williams` / `teacher123`
- Go to **Mark Attendance** → Mark individual or bulk attendance
- Go to **Reports** → Generate course and student reports
- View interactive charts and visualizations

### 3. Student Features
- Login as `john_doe` / `student123`
- Go to **My Reports** → View personal attendance
- Filter by date ranges
- View attendance statistics and charts

## Sample Data Included

The application comes pre-loaded with:
- **8 Courses**: Mathematics, Physics, Chemistry, Biology, Computer Science, English Literature, History, Art
- **10 Students**: All with login credentials
- **5 Teachers**: All with login credentials
- **30 Days of Sample Attendance**: Random attendance records for testing

## Troubleshooting

### If the application doesn't start:
1. **Check Java Version**: Ensure Java 21+ is installed
2. **Check Maven**: Ensure Maven is installed and accessible
3. **Clean Build**: Run `mvn clean compile` first
4. **Check Port**: Ensure port 8080 is not in use

### If you get lazy loading errors:
- The issue has been fixed in the DataInitializer
- The application should start without errors now

### If you can't access the application:
1. Wait for the application to fully start (look for "Started SttudentAttendanceApplication")
2. Check that the application is running on port 8080
3. Try accessing http://localhost:8080/login

## Features Highlights

- **Interactive Enrollment**: Modal-based student enrollment in courses
- **Data Visualization**: Charts and graphs for attendance reports
- **Role-based Access**: Different interfaces for admin, teacher, and student
- **Responsive Design**: Works on desktop and mobile devices
- **Sample Data**: Ready-to-use test data for immediate testing

## Mobile Friendly

The application is fully responsive and works on:
- Desktop computers
- Tablets
- Mobile phones

Enjoy testing the Student Attendance Management System! 


