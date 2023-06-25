package com.ujobs.WebServices.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ujobs.WebServices.dto.EmployerDto;
import com.ujobs.WebServices.dto.UserDto;
import com.ujobs.WebServices.exception.ResourceNotFoundException;
import com.ujobs.WebServices.model.Employer;
import com.ujobs.WebServices.model.User;
import com.ujobs.WebServices.repository.EmployerRepository;
import com.ujobs.WebServices.service.EmployerService;

@Service
public class EmployerServiceImpl implements EmployerService {

    @Autowired
    private EmployerRepository employerRepository;

    @Override
    public EmployerDto getEmployerById(Long id) {
        Employer employer = employerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el empleador con el id: " + id));
        return mapToDto(employer);
    }

    @Override
    public Employer getEmployerByDni(String dni) {
        return employerRepository.findByDni(dni);
    }

    @Override
    public Employer getEmployerByEmail(String email) {
        return employerRepository.findByEmail(email);
    }

    @Override
    public List<Employer> getEmployersByName(String name) {
        return employerRepository.findByName(name);
    }

    @Override
    public List<Employer> getEmployersByLastName(String lastName) {
        return employerRepository.findByLastName(lastName);
    }

    @Override
    public List<Employer> getEmployersByCompanyName(String companyName) {
        return employerRepository.findByCompanyName(companyName);
    }

    @Override
    public Employer updateEmployer(Employer employer) {
        Optional<Employer> existingEmployer = employerRepository.findById(employer.getId());
        if (existingEmployer.isPresent()) {
            Employer employerToUpdate = existingEmployer.get();

            employerToUpdate.setName(employer.getName());
            employerToUpdate.setLastName(employer.getLastName());
            employerToUpdate.setEmail(employer.getEmail());
            employerToUpdate.setCompanyName(employer.getCompanyName());
            employerToUpdate.setRuc(employer.getRuc());
            employerToUpdate.setJobPosition(employer.getJobPosition());

            return employerRepository.save(employerToUpdate);
        } else {
            throw new ResourceNotFoundException("No se encontro el empleador con el id: " + employer.getId());
        }
    }

    @Override
    public void deleteEmployer(Employer employer) {
        employerRepository.delete(employer);
    }

    @Override
    public Employer changeEmployerPassword(Long employerId, String newPassword) {
        Optional<Employer> existingEmployer = employerRepository.findById(employerId);

        if (existingEmployer.isPresent()) {
            Employer employerToUpdate = existingEmployer.get();
            employerToUpdate.setPassword(newPassword);

            return employerRepository.save(employerToUpdate);
        } else {
            throw new ResourceNotFoundException("No se encontro el empleador con el id: " + employerId);
        }
    }

    private EmployerDto mapToDto(Employer employer) {
        EmployerDto EmployerDto = new EmployerDto();
        EmployerDto.setCompanyName(employer.getCompanyName());
        EmployerDto.setRuc(employer.getRuc());
        EmployerDto.setCompanyName(employer.getCompanyName());
        EmployerDto.setUser(mapUserDto(employer));
        return EmployerDto;
    }

    private UserDto mapUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        return userDto;
    }

}
