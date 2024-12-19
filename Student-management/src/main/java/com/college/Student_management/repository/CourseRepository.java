package com.college.Student_management.repository;

import com.college.Student_management.entity.Course;
import com.college.Student_management.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course>
    findByName(String name);

    @Query("SELECT s FROM Student s JOIN CourseYearStudent cys ON s.id = cys.student.id " +
            "WHERE cys.course.courseNo = :courseNo AND cys.year.year = :year")
    List<Student> findStudentsByCourseNoAndYear(@Param("courseNo") String courseNo, @Param("year") String year);

    Optional<Course> findByCourseNo(String courseNo);
}