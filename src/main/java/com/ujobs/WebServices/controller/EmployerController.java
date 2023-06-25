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

import com.ujobs.WebServices.dto.EmployerDto;
import com.ujobs.WebServices.service.EmployerService;


@RestController
@RequestMapping("/api/v1/employer")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployerController {
    
    @Autowired
    private EmployerService employerService;

    //URL: http://localhost:8080/api/v1/employer/{id}
    //Method: GET

    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public ResponseEntity<EmployerDto> getEmployerById(@PathVariable(value = "id") Long id) {
        EmployerDto employerDto = employerService.getEmployerById(id);
        return new ResponseEntity<>(employerDto, HttpStatus.OK);
    }
    
}
