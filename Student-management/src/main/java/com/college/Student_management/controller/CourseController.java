package com.college.Student_management.controller;

import com.college.Student_management.NotFoundException;
import com.college.Student_management.ResourceAlreadyExistsException;
import com.college.Student_management.dto.CourseDTO;
import com.college.Student_management.dto.StudentDTO;
import com.college.Student_management.entity.*;
import com.college.Student_management.repository.CourseRepository;
import com.college.Student_management.repository.CourseYearStudentRepository;
import com.college.Student_management.repository.StudentRepository;
import com.college.Student_management.repository.YearRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseYearStudentRepository courseYearStudentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private YearRepository yearRepository;

    @GetMapping(produces = {"application/json", "application/xml"})
    public List<CourseDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream().map(this::convertTODTO).toList();
    }

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    public CourseDTO getCourseById(@PathVariable Long id) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isPresent()) {
            return convertTODTO(course.get());
        } else {
            throw new NotFoundException("Course with ID " + id + " not found");
        }
    }

    @GetMapping(value = "/{courseNo}/year/{year}/student", produces = {"application/json", "application/xml"})
    public List<StudentDTO> getStudentsEnrolledByCourseAndYear(@PathVariable String courseNo, @PathVariable String year) {
        List<Student> studentsEnrolledInCourse = courseRepository.findStudentsByCourseNoAndYear(courseNo, year);

        if (studentsEnrolledInCourse.isEmpty()) {
            throw new NotFoundException("No students found for course " + courseNo + " and year " + year);
        }

        List<StudentDTO> studentDTOs = studentsEnrolledInCourse.stream()
                .map(s -> new StudentDTO(s.getId(), s.getName(), s.getEmail(), s.getAddress()))
                .collect(Collectors.toList());

        return studentDTOs;
    }

    @PostMapping(value = "/{courseNo}/year/{year}/students", consumes = {"application/json"}, produces = {"application/json", "application/xml"})
    public List<CourseYearStudent>  enrollStudentsInCourse(
            @PathVariable String courseNo,
            @PathVariable String year,
            @RequestBody List<StudentDTO> studentDTOs) {

        Course course = courseRepository.findByCourseNo(courseNo)
                .orElseThrow(() -> new NotFoundException("Course not found for courseNo: " + courseNo));

        Year courseYear = yearRepository.findByYear(year)
                .orElseThrow(() -> new NotFoundException("Year not found for year: " + year));

        List<CourseYearStudent> enrollments = new ArrayList<>();
        for (StudentDTO dto : studentDTOs) {
            if (dto.getId() == null) {
                throw new IllegalArgumentException("Student ID cannot be null.");
            }
            Student student = studentRepository.findById(dto.getId())
                    .orElseThrow(() -> new NotFoundException("Student not found for ID: " + dto.getId()));

            CourseYearStudentId courseYearStudentId = new CourseYearStudentId();
            courseYearStudentId.setCourseId(course.getId());
            courseYearStudentId.setStudentId(student.getId());
            courseYearStudentId.setYearId(courseYear.getId());

            CourseYearStudent enrollment = CourseYearStudent.builder()
                    .id(courseYearStudentId)
                    .course(course)
                    .year(courseYear)
                    .student(student)
                    .build();

            enrollments.add(enrollment);

        }
        courseYearStudentRepository.saveAll(enrollments);

        return enrollments;

    }

    @PostMapping(produces = {"application/json", "application/xml"})
    public Course createCourse(@RequestBody CourseDTO courseDTO) {
        Optional<Course> existingCourse = courseRepository.findByName(courseDTO.getName());
        if (existingCourse.isPresent()) {
            throw new ResourceAlreadyExistsException("course " + courseDTO.getName() + " already exist");
        }
        return courseRepository.save(convertTOCourse(courseDTO));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleCourseNotFoundException(NotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Course Not Found");
        body.put("message", ex.getMessage());
        body.put("path", "/api/course");

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Object> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error", "Conflict");
        body.put("message", ex.getMessage());
        body.put("path", "/api/course");

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    private CourseDTO convertTODTO(Course course) {
        CourseDTO courseDTO = new CourseDTO();
        BeanUtils.copyProperties(course, courseDTO);
        return courseDTO;
    }

    private Course convertTOCourse(CourseDTO courseDTO) {
        Course course = new Course();
        BeanUtils.copyProperties(courseDTO, course);
//        course.setId(courseDTO.getId());
//        course.setName(courseDTO.getName());
//        course.setDuration(courseDTO.getDuration());
//        course.setCourseNo(courseDTO.getCourseNo());
        return course;
    }
}
