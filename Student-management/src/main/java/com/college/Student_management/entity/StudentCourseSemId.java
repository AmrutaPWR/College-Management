package com.college.Student_management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class StudentCourseSemId implements Serializable {

    @Column(name = "student_id")
    private Long studentId;

    @Column(name= "course_id")
    private Long courseId;

    @Column(name= "semester_id")
    private Long semId;

}
