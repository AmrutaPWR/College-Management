package com.college.Student_management.repository;

import com.college.Student_management.entity.CourseYearStudent;
import com.college.Student_management.entity.CourseYearStudentId;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CourseYearStudentRepository extends JpaRepository<CourseYearStudent, CourseYearStudentId> {

}