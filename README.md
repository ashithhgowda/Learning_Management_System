# Learning Management System (LMS)

## Project Overview
This project is a **Learning Management System (LMS)** built using **Java (Spring Boot)**.  
It allows **students and instructors** to manage courses, enrollments, and grading efficiently.  
The system includes login functionality for both students and instructors, with REST APIs tested via **Postman**.

---

## Features
- **Student Login & Management**
  - Students can log in using credentials.
  - View personal details, enrolled courses, and grades.

- **Instructor Login & Management**
  - Instructors can log in to manage courses.
  - View student enrollments and update grades.

- **Course Management**
  - Add, update, delete, and view courses.

- **Enrollment Management**
  - Students can enroll in courses.
  - Track which students are enrolled in which courses.

- **REST API Integration**
  - All functionalities are exposed as REST endpoints.
  - Test endpoints using **Postman**.

---

## Entities
1. **Student** – Stores student information (name, email, enrolled courses).
2. **Instructor** – Stores instructor information and courses they teach.
3. **Course** – Stores course details (name, description, instructor).
4. **Enrollment** – Tracks student-course enrollments.

---

## How It Works

1. **Login**
   - Students and instructors log in using the login form at [http://localhost:8080/login](http://localhost:8080/login).
   - Correct credentials redirect users to their respective dashboards.

2. **Student Dashboard**
   - Displays student details and enrolled courses.
   - Students can view grades and course info.

3. **Instructor Dashboard**
   - Displays instructor details and managed courses.
   - Instructors can view enrolled students and update grades.

4. **REST API with Postman**
   - Test login and CRUD operations via Postman.
   - Example endpoints:
     - `POST /login` – login
     - `GET /students` – list students
     - `POST /courses` – add course
     - `POST /enrollments` – enroll student

---

## How to Run in Eclipse

1. **Import Project**
   - Eclipse → File → Import → Existing Maven Project → Select project folder.

2. **Configure Database**
   - Update `application.properties` with your database credentials (H2/MySQL).

3. **Run the Application**
   - Right-click project → Run As → Spring Boot App.
   - Open [http://localhost:8080/login](http://localhost:8080/login) in browser.

4. **Testing via Postman**
   - Send HTTP requests to endpoints, e.g., login:
   ```json
   POST http://localhost:8080/login
   {
     "username": "student1",
     "password": "password123"
   }
![p4](https://github.com/user-attachments/assets/313f8001-c4f4-4419-898e-adbe03a90da2)
![p3](https://github.com/user-attachments/assets/acb72191-6d36-45c2-a334-ecf308ce8a08)
![p2](https://github.com/user-attachments/assets/95ed4dbf-f546-4ee9-bd55-92fbf9b63d78)
![p1](https://github.com/user-attachments/assets/b9b4cb1c-a6a8-43a5-a98f-6fa03b37ee3e)

## Technologies Used

-Java 17
-Spring Boot
-Maven
-REST API
-Postman (testing)
-H2 / MySQL Database

## Future Enhancements

-Role-based access control for security.
-Add a frontend interface using HTML/CSS/React.
-Reporting and analytics for student performance.
