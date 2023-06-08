package com.ujobs.WebServices.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ujobs.WebServices.dto.CareerDto;
import com.ujobs.WebServices.model.Career;
import com.ujobs.WebServices.repository.CareerRepository;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class CareerController {

    private final CareerRepository careerRepository;
    private final ModelMapper modelMapper;

    public CareerController(CareerRepository careerRepository, ModelMapper modelMapper) {
        this.careerRepository = careerRepository;
        this.modelMapper = modelMapper;
    }

    // URL: http://localhost:8080/api/v1/career
    // Method: POST
    @Transactional
    @PostMapping("/career")
    public ResponseEntity<Career> registerStudent(@RequestBody Career request) {
        return new ResponseEntity<Career>(careerRepository.save(request), HttpStatus.CREATED);
    }

    // URL: http://localhost:8080/api/v1/career
    // Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/career")
    public ResponseEntity<List<CareerDto>> getAllCareers() {
        List<Career> careers = careerRepository.findAll();
        List<CareerDto> careerDtos = careers.stream()
                .map(career -> modelMapper.map(career, CareerDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(careerDtos);
    }

}
