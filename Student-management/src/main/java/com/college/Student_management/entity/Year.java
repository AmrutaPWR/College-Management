package com.college.Student_management.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "year")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Year {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "id")
    private Long id;

    @Column(name = "year")
    private String year;
}
