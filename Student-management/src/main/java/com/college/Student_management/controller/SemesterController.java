package com.college.Student_management.controller;

import com.college.Student_management.NotFoundException;
import com.college.Student_management.ResourceAlreadyExistsException;
import com.college.Student_management.dto.SemesterDTO;
import com.college.Student_management.entity.Semester;
import com.college.Student_management.repository.SemesterRepository;
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
@RequestMapping("/api/semester")
public class SemesterController {

    @Autowired
    private SemesterRepository semesterRepository;

    @GetMapping
    public List<SemesterDTO> getAllSemesters() {
        List<Semester> semester = semesterRepository.findAll();
        return semester.stream().map(this::convertToDTO).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SemesterDTO> getSemesterById(@PathVariable Long id) {
        Optional<Semester> semester = semesterRepository.findById(id);
        if (semester.isPresent()) {
            return ResponseEntity.ok(convertToDTO(semester.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Semester createSemester(@RequestBody SemesterDTO semesterDTO) {
        Optional<Semester> semester = semesterRepository.findById(semesterDTO.getId());

        if (semester.isPresent()) {
            throw new ResourceAlreadyExistsException("semester with Id " + semesterDTO.getId() + " already exist");

        }else
            return semesterRepository.save(convertToSemester(semesterDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SemesterDTO> updateSemester(@PathVariable Long id, @RequestBody Semester semesterDetails) {
        Optional<Semester> optionalSemester = semesterRepository.findById(id);

        if (optionalSemester.isPresent()) {
            Semester existingSemester = optionalSemester.get();
            existingSemester.setSem(semesterDetails.getSem());
            existingSemester.setMonth(semesterDetails.getMonth());

            Semester updatedSemester = semesterRepository.save(existingSemester);
            return ResponseEntity.ok(convertToDTO(updatedSemester));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSemester(@PathVariable Long id) {
        Optional<Semester> optionalSemester = semesterRepository.findById(id);
        if (optionalSemester.isPresent()) {
            semesterRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleCourseNotFoundException(NotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Semester Not Found");
        body.put("message", ex.getMessage());
        body.put("path", "/api/semester");

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Object> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error", "Conflict");
        body.put("message", ex.getMessage());
        body.put("path", "/api/semester");

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    private Semester convertToSemester(SemesterDTO semesterDTO) {
        Semester semester = new Semester();
        BeanUtils.copyProperties(semesterDTO, semester);
        return semester;
    }

    private SemesterDTO convertToDTO(Semester semester) {
        SemesterDTO semesterDTO = new SemesterDTO();
        BeanUtils.copyProperties(semester, semesterDTO);
        return semesterDTO;
    }
}
