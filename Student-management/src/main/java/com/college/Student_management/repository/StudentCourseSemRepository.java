package com.college.Student_management.repository;

import com.college.Student_management.entity.StudentCourseSem;
import com.college.Student_management.entity.StudentCourseSemId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

//public interface StudentCourseSemRepository extends JpaRepository<StudentCourseSem, StudentCourseSemId> {
//    List<StudentCourseSem> findByIdId(Long studentId);
//
//}


public interface StudentCourseSemRepository extends JpaRepository<StudentCourseSem, StudentCourseSemId> {

    Optional<StudentCourseSem> findById_StudentIdAndId_CourseIdAndId_SemId(Long studentId, Long courseId, Long semId);
}