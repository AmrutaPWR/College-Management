package com.college.Student_management.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Table(name = "student")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE)
    private Set<StudentCourseSem> courseEnrolledByStudent;

    @OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE)
    private Set<CourseYearStudent> courses;
}
