package com.ujobs.WebServices.service;

import java.util.List;

import com.ujobs.WebServices.dto.EmployerDto;
import com.ujobs.WebServices.model.Employer;

public interface EmployerService {

    public abstract EmployerDto getEmployerById(Long id);

    public abstract Employer getEmployerByDni(String dni);

    public abstract Employer getEmployerByEmail(String email);

    public abstract List<Employer> getEmployersByName(String name);

    public abstract List<Employer> getEmployersByLastName(String lastName);

    public abstract List<Employer> getEmployersByCompanyName(String companyName);

    public abstract Employer updateEmployer(Employer employer);

    public abstract void deleteEmployer(Employer employer);

    public abstract Employer changeEmployerPassword(Long employerId, String newPassword);
    
}
