package com.college.Student_management.repository;

import com.college.Student_management.entity.Course;
import com.college.Student_management.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemesterRepository extends JpaRepository<Semester, Long> {

}
