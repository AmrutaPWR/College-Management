package com.college.Student_management;

import com.college.Student_management.entity.Semester;
import com.college.Student_management.repository.SemesterRepository;
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

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SemesterRepositoryTests {

    @Autowired
    private SemesterRepository semesterRepository;

    @Test
    @Order(1)
    public void saveSemTest() {
        Semester sem = Semester.builder()
                .sem("1st")
                .month("July-Dec")
                .build();

        semesterRepository.save(sem);

        Assertions.assertThat(sem.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getListOfSemTest(){
        List<Semester> sem = semesterRepository.findAll();
        Assertions.assertThat(sem.size()).isGreaterThan(0);
    }
    @Test
    @Order(3)
    public void getSemTest(){
        Semester semester = semesterRepository.findById(1).get();
        Assertions.assertThat(semester.getId()).isGreaterThan(0);
    }
    @Test
    @Order(4)
    public void updateSemTest(){
        Semester sem = semesterRepository.findById(1).get();
        sem.setSem("2nd");
        sem.setMonth("Jan-June");

        Semester semUpdated = semesterRepository.save(sem);
        Assertions.assertThat(semUpdated.getSem()).isEqualTo("2nd");
        Assertions.assertThat(semUpdated.getMonth()).isEqualTo("Jan-June");
    }

    @Test
    @Order(5)
    public void deleteSem(){
        Semester sem = semesterRepository.findById(1).get();
        Assertions.assertThat(sem.getId()).isNotNull();

        semesterRepository.deleteById(sem.getId());

        Optional<Semester> deletedSem = semesterRepository.findById(sem.getId());
        Assertions.assertThat(deletedSem).isEmpty();

    }
}
