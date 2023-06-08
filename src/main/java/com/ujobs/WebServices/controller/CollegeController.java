package com.ujobs.WebServices.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ujobs.WebServices.dto.CollegeDto;
import com.ujobs.WebServices.model.Career;
import com.ujobs.WebServices.model.College;
import com.ujobs.WebServices.repository.CollegeRepository;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class CollegeController {

    private final CollegeRepository collegeRepository;

    public CollegeController(CollegeRepository collegeRepository) {
        this.collegeRepository = collegeRepository;
    }

    @Transactional
    @PostMapping("/college")
    public ResponseEntity<College> registerCollege(@RequestBody College request) {
        return new ResponseEntity<College>(collegeRepository.save(request), HttpStatus.CREATED);
    }

    @Transactional(readOnly = true)
    @GetMapping("/college")
    public ResponseEntity<List<CollegeDto>> getColleges() {
        List<College> colleges = collegeRepository.findAll();
        List<CollegeDto> collegeDTOs = convertToDTOs(colleges);
        return new ResponseEntity<List<CollegeDto>>(collegeDTOs, HttpStatus.OK);
    }

    private List<CollegeDto> convertToDTOs(List<College> colleges) {
        List<CollegeDto> collegeDTOs = new ArrayList<>();

        for (College college : colleges) {
            CollegeDto dto = new CollegeDto();

            dto.setId(college.getId());
            dto.setCollegeName(college.getCollegeName());

            List<String> careerNames = new ArrayList<>();
            for (Career career : college.getCareers()) {
                careerNames.add(career.getCareerName());
            }

            dto.setCareerNames(careerNames);
            collegeDTOs.add(dto);
        }

        return collegeDTOs;
    }
}