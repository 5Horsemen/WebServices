package com.ujobs.WebServices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ujobs.WebServices.model.College;

public interface CollegeRepository extends JpaRepository<College, Long> {
    College findByCollegeName(String collegeName);
}
