This project is a College Management System developed with Spring Boot for managing various college-related operations like student registration, course management, and more. 
It uses Liquibase for database version control, Lombok for reducing boilerplate code, Hibernate for ORM, and MySQL as the database.

Features
Student Management
Course Management
RESTful APIs for CRUD operations
Database versioning with Liquibase
ORM with Hibernate
Easy setup with Lombok
Technologies Used
Spring Boot: Backend framework for building the application.
Liquibase: Database version control.
Lombok: Reduces boilerplate code by using annotations.
MySQL: Relational database management system.
Hibernate: Object-relational mapping (ORM) framework for managing database interaction.
Maven: Build automation tool.
Prerequisites
Java 8 or higher
Maven 3.6 or higher
MySQL 5.7 or higher
Liquibase
Lombok
IDE (IntelliJ IDEA, Eclipse, etc.)
Setup Instructions
1. Clone the Repository
bash
Copy code
git clone https://github.com/AmrutaPWR/college-management.git
2. Setup MySQL Database
Install MySQL and configure it.
Create a new database for the project: 
sql
Copy code
CREATE DATABASE college_management_db;
Update application.properties with your MySQL credentials.
properties
Copy code
spring.datasource.url=jdbc:mysql://localhost:3306/college_management_db
spring.datasource.username=your_username
spring.datasource.password=your_password
3. Run Liquibase Migrations
Liquibase will handle the creation and updates of the database schema. The changelog files for Liquibase are available in src/main/resources/db/changelog.

To run Liquibase migrations:

bash
Copy code
mvn liquibase:update
4. Build the Project
bash
Copy code
mvn clean install
5. Run the Application
bash
Copy code
mvn spring-boot:run
Alternatively, you can run the application from your IDE.

6. Access the Application
Once the application is running, you can access the APIs or front end (if applicable) at:

arduino
Copy code
http://localhost:8080
Project Structure
bash
Copy code
src
├── main
│   ├── java
│   │   └── com.example.collegemanagement
│   ├── resources
│   │   ├── application.properties
│   │   ├── db
│   │   │   └── changelog
│   │   │       └── db.changelog-master.xml
│   │   └── templates
├── test
│   └── java
│       └── com.example.collegemanagement
Notable Packages
controller: Contains REST controllers for handling HTTP requests.
service: Business logic and service layer classes.
repository: Contains Spring Data JPA repositories for interacting with the database.
model: Entity classes that map to database tables.
dto: Data transfer objects for API requests and responses.
Lombok
This project uses Lombok to reduce boilerplate code for model classes (like getters, setters, constructors, etc.). Make sure your IDE supports Lombok:

For IntelliJ IDEA: Install the Lombok plugin.
For Eclipse: Install Lombok from the marketplace.
API Endpoints
Below are some key API endpoints available in the system:

Student Management

API Endpoints
Course Management
Get all Courses

URL: GET /api/course
Description: Retrieves a list of all courses.
Response: JSON/XML list of CourseDTO objects.
Get Course by ID

URL: GET /api/course/{id}
Description: Retrieves a specific course by its ID.
Response: JSON/XML CourseDTO object.
Error: Throws NotFoundException if the course is not found.
Get Students Enrolled in a Course by Year

URL: GET /api/course/{courseNo}/year/{year}/student
Description: Retrieves all students enrolled in a specific course for a specific year.
Response: JSON/XML list of StudentDTO objects.
Error: Throws NotFoundException if no students are found for the course and year combination.
Enroll Students in a Course

URL: POST /api/course/{courseNo}/year/{year}/students
Description: Enrolls a list of students in a course for a specific year.
Request Body: List of StudentDTO objects.
Response: JSON/XML list of CourseYearStudent objects representing the enrollments.
Error: Throws NotFoundException if the course, year, or student is not found.
Create a New Course

URL: POST /api/course
Description: Creates a new course.
Request Body: JSON CourseDTO object.
Response: The created Course entity.
Error: Throws ResourceAlreadyExistsException if a course with the same name already exists.
Student Management
Get all Students

URL: GET /api/student
Description: Retrieves a list of all students.
Response: JSON list of StudentDTO objects.
Get Student by ID

URL: GET /api/student/{id}
Description: Retrieves a specific student by their ID.
Response: JSON StudentDTO object.
Error: Throws NotFoundException if the student is not found.
Create a New Student

URL: POST /api/student
Description: Creates a new student.
Request Body: JSON StudentDTO object.
Response: The created Student entity.
Error: Throws ResourceAlreadyExistsException if a student with the same email already exists.
Update Student

URL: PUT /api/student/{id}
Description: Updates a student’s details by their ID.
Request Body: JSON StudentDTO object with updated fields.
Response: The updated StudentDTO object.
Error: Throws NotFoundException if the student is not found.
Delete Student

URL: DELETE /api/student/{id}
Description: Deletes a student by their ID.
Error: Throws NotFoundException if the student is not found.
Running Tests
To run the test cases, execute the following command:

bash
Copy code
mvn test
Future Enhancements
Add front-end integration (Angular or React).
Add user roles and permissions.
Add email notification for important events like exam schedules.
Contributing
Feel free to raise issues or create pull requests. All contributions are welcome!
