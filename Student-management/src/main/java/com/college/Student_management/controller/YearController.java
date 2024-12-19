package com.college.Student_management.controller;

import com.college.Student_management.NotFoundException;
import com.college.Student_management.ResourceAlreadyExistsException;
import com.college.Student_management.dto.YearDTO;
import com.college.Student_management.entity.Year;
import com.college.Student_management.repository.YearRepository;
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
@RequestMapping("/api/year")
public class YearController {

    @Autowired
    private YearRepository yearRepo;

    @GetMapping(produces = {"application/json", "application/xml"})
    public List<YearDTO> getAllBatches() {
        List<Year> batches = yearRepo.findAll();
        return batches.stream().map(this::convertTODTO).toList();
    }

    @GetMapping(value ="/{id}", produces = {"application/json", "application/xml"})
    public YearDTO getYearById(@PathVariable Long id) {
        Optional<Year> year = yearRepo.findById(id);
        if (year.isPresent()) {
            return convertTODTO(year.get());
        } else {
            throw new NotFoundException("Year with ID " + id + " not found");
        }
    }

    @PostMapping(produces = {"application/json", "application/xml"})
    public Year addYear(@RequestBody YearDTO yearDTO) {
        Optional<Year> existingYear = yearRepo.findById(yearDTO.getId());
        if(existingYear.isPresent()){
            throw new ResourceAlreadyExistsException("year " + yearDTO.getYear() + " already exist");
        }
        return yearRepo.save(convertTOYear(yearDTO));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleCourseNotFoundException(NotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Year Not Found");
        body.put("message", ex.getMessage());
        body.put("path", "/api/year");

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Object> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error", "Conflict");
        body.put("message", ex.getMessage());
        body.put("path", "/api/year");

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    private YearDTO convertTODTO(Year year){
        YearDTO yearDTO = new YearDTO();
        BeanUtils.copyProperties(year,yearDTO);
        return  yearDTO;
    }

    private Year convertTOYear(YearDTO yearDTO){
        Year year = new Year();
        BeanUtils.copyProperties(yearDTO,year);
        return  year;
    }

}
