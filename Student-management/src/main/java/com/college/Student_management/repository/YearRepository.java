package com.college.Student_management.repository;

import com.college.Student_management.entity.Course;
import com.college.Student_management.entity.Year;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface YearRepository extends JpaRepository<Year, Long> {
    Optional<Year> findByYear(String year);
}
