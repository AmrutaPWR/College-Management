package com.college.Student_management;

import com.college.Student_management.entity.Course;
import com.college.Student_management.entity.StudentCourseSem;
import com.college.Student_management.repository.CourseRepository;
import com.college.Student_management.repository.StudentCourseSemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // Disable embedded database replacement
//@Transactional  // Ensure the test runs in a transaction
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CourseRepositoryTests {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentCourseSemRepository studentCourseRepository;

    @Test
    @Order(1)
    public void saveCourseTest() {

        Course course = Course.builder()
                .course_name("MBA")
                .course_duration("2 yrs")
                .build();

        courseRepository.save(course);

        Assertions.assertThat(course.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getListOfCourseTest(){
        List<Course> courses = courseRepository.findAll();
        Assertions.assertThat(courses.size()).isGreaterThan(0);
    }
    @Test
    @Order(3)
    public void getCourseTest(){
        Course course = courseRepository.findById(1L).get();
        Assertions.assertThat(course.getId()).isGreaterThan(0);
    }
    @Test
    @Order(4)
    public void updateCourseTest(){
        Course course = courseRepository.findById(1L).get();
        course.setCourse_name("BCA");
        course.setCourse_duration("3 yrs");

        Course courseUpdated = courseRepository.save(course);
        Assertions.assertThat(courseUpdated.getCourse_name()).isEqualTo("BCA");
        Assertions.assertThat(courseUpdated.getCourse_duration()).isEqualTo("3 yrs");
    }

    @Test
    @Order(5)
    public void deleteCourse() {
        Optional<Course> optionalCourse = courseRepository.findById(1L);

        Assertions.assertThat(optionalCourse).isPresent();

        Course course = optionalCourse.get();

        List<StudentCourseSem> studentCourseList = studentCourseRepository.findByIdId(1L);

        studentCourseRepository.deleteAll(studentCourseList);

        courseRepository.delete(course);

        Optional<Course> deletedCourse = courseRepository.findById(1L);
        Assertions.assertThat(deletedCourse).isNotPresent();
    }

}
