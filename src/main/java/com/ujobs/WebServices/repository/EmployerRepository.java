package com.ujobs.WebServices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ujobs.WebServices.model.Employer;

public interface EmployerRepository extends JpaRepository<Employer, Long> {

}
