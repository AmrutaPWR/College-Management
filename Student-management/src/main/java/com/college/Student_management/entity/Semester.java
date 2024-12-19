package com.college.Student_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "semester")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Semester {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "id")
    private Long id;

    @Column(name = "sem")
    private String sem;
    
    @Column(name = "month")
    private String month;

    @OneToMany(mappedBy = "semester")
    private Set<StudentCourseSem> courseEnrolledByStudent;
}
