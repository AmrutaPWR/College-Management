package com.college.Student_management.controller;

import com.college.Student_management.NotFoundException;
import com.college.Student_management.ResourceAlreadyExistsException;
import com.college.Student_management.dto.StudentDTO;
import com.college.Student_management.entity.Student;
import com.college.Student_management.repository.StudentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(this::convertToDTO).toList();
    }

    @GetMapping("/{id}")
    public StudentDTO getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            return convertToDTO(student.get());
        } else {
            throw new NotFoundException("Student with ID " + id + " not found");
        }
    }

    @PostMapping
    public Student createStudent(@RequestBody StudentDTO student) {
        Optional<Student> existingStudent = studentRepository.findByEmail(student.getEmail());

        if (existingStudent.isPresent()) {
            throw new ResourceAlreadyExistsException("Student with email " + student.getEmail() + " already exists.");
        }
        Student student1 = convertToEntity(student);
        return studentRepository.save(student1);
    }

    @PutMapping("/{id}")
    public StudentDTO updateStudent(@PathVariable Long id, @RequestBody StudentDTO studentDetails) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("student with id " + id + " not found"));

        student.setName(studentDetails.getName());
        student.setEmail(studentDetails.getEmail());
        student.setAddress(studentDetails.getAddress());

        studentRepository.save(student);
        return convertToDTO(student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            studentRepository.deleteById(id);
        }
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Object> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error", "Conflict");
        body.put("message", ex.getMessage());
        body.put("path", "/api/student");

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleStudentNotFoundException(NotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Not Found");
        body.put("message", ex.getMessage());
        body.put("path", "/api/students");

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    private StudentDTO convertToDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
//        if (student.getId() != null) {
//            studentDTO.setId(student.getId());
//        }
        BeanUtils.copyProperties(student, studentDTO);
        return studentDTO;
    }

    private Student convertToEntity(StudentDTO studentDTO) {
        Student student = new Student();

        BeanUtils.copyProperties(studentDTO, student);
        return student;
    }

}
