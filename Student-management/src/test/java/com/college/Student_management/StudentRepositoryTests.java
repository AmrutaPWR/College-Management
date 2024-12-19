package com.college.Student_management;

import com.college.Student_management.entity.Student;
import com.college.Student_management.entity.StudentCourseSem;
import com.college.Student_management.repository.StudentCourseSemRepository;
import com.college.Student_management.repository.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // Disable embedded database replacement
@Transactional  // Ensure the test runs in a transaction
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentRepositoryTests {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentCourseSemRepository studentCourseSemRepository;
    @Test
    @Order(1)
    public void saveStudentTest() {

        Student student = Student.builder()
                .name("Student1")
                .email("pqr@gmail.com")
                .address("XYZ")
                .build();

        studentRepository.save(student);

        Assertions.assertThat(student.getStudent_id()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getListOfStudentTest(){
        List<Student> students = studentRepository.findAll();
        Assertions.assertThat(students.size()).isGreaterThan(0);
    }
    @Test
    @Order(3)
    public void getStudentTest(){
        Student student = studentRepository.findById(1L).get();
        Assertions.assertThat(student.getStudent_id()).isGreaterThan(0);
    }
    @Test
    @Order(4)
    public void updateStudentTest(){
        Student student = studentRepository.findById(1L).get();
        student.setName("student2");
        student.setEmail("ABCD@gmail.com");
        student.setAddress("PQR");

        Student studentUpdated = studentRepository.save(student);
        Assertions.assertThat(studentUpdated.getName()).isEqualTo("student2");
        Assertions.assertThat(studentUpdated.getEmail()).isEqualTo("ABCD@gmail.com");
        Assertions.assertThat(studentUpdated.getAddress()).isEqualTo("PQR");
    }

    @Test
    @Order(5)
    public void deleteStudent(){

        Long studentId = 1L;
        Student student = studentRepository.findById(studentId).orElse(null);
        Assertions.assertThat(student).isNotNull();

        List<StudentCourseSem> studentCourseSems = studentCourseSemRepository.findByIdId(studentId);
        Assertions.assertThat(studentCourseSems).isNotEmpty();

        studentRepository.delete(student);

        Student deletedStudent = studentRepository.findById(studentId).orElse(null);
        Assertions.assertThat(deletedStudent).isNull();

        List<StudentCourseSem> studentCoursesAfterDelete = studentCourseSemRepository.findByIdId(studentId);
        Assertions.assertThat(studentCoursesAfterDelete).isEmpty();

    }

}
