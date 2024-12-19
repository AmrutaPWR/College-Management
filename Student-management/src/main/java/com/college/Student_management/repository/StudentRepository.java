package com.college.Student_management.repository;

import com.college.Student_management.dto.StudentDTO;
import com.college.Student_management.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);


}