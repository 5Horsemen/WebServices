package com.ujobs.WebServices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ujobs.WebServices.model.Career;

public interface CareerRepository extends JpaRepository<Career, Long> {
    Career findByCareerName(String careerName);
}
