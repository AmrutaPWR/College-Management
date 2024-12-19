package com.college.Student_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "student_course_sem")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentCourseSem {

    @EmbeddedId
    StudentCourseSemId id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @MapsId("semId")
    @JoinColumn(name = "semester_id")
    private Semester semester;
}
