package com.college.Student_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "course_year_student")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseYearStudent {

    @EmbeddedId
    CourseYearStudentId id;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @MapsId("yearId")
    @JoinColumn(name = "year_id")
    private Year year;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

}
