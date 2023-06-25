package com.ujobs.WebServices.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ujobs.WebServices.model.Employer;

public interface EmployerRepository extends JpaRepository<Employer, Long> {

    List<Employer> findByCompanyName(String companyName);
    
    Employer findByDni(String dni);

    Employer findByEmail(String email);

    List<Employer> findByName(String name);

    List<Employer> findByLastName(String lastName);
}
