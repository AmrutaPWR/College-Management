package com.college.Student_management.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentDTO {

    private Long id;

    private String name;

    private String email;

    private String address;

}
