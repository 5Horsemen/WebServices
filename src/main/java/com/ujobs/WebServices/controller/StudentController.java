package com.ujobs.WebServices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ujobs.WebServices.dto.StudentDto;
import com.ujobs.WebServices.service.StudentService;

@RestController
@RequestMapping("/api/v1/student")
@CrossOrigin(origins = "http://localhost:4200")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // URL: http://localhost:8080/api/v1/student/{id}
    // Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable(value = "id") Long id) {
        StudentDto studentDto = studentService.getStudentById(id);
        return new ResponseEntity<>(studentDto, HttpStatus.OK);
    }

}
