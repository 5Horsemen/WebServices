package com.ujobs.WebServices.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ujobs.WebServices.exception.ValidationException;
import com.ujobs.WebServices.model.Student;
import com.ujobs.WebServices.requests.AuthenticationRequest;
import com.ujobs.WebServices.requests.StudentRegistrationRequest;
import com.ujobs.WebServices.response.AuthenticationResponse;
import com.ujobs.WebServices.service.AuthentificationService;
import com.ujobs.WebServices.service.StudentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/account")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private AuthentificationService authentificationService;

    // URL: http://localhost:8080/api/v1/account/register/student
    // Method: POST
    // Json: {
    // "student": {
    // "name": "John",
    // "lastName": "Doe",
    // "email": "john.doe@example.com",
    // "password": "mySecurePassword",
    // "dni": "1234567",
    // "universityEmail": "john.doe@university.edu"
    // },
    // "collegeId": 1,
    // "careerId": 1
    // }
    @Transactional
    @PostMapping("/register/student")
    public ResponseEntity<AuthenticationResponse> registerStudent(@RequestBody StudentRegistrationRequest request) {
        validateStudent(request.getStudent());
        AuthenticationResponse registeredStudent = authentificationService.registerStudent(request.getStudent(),
                request.getCollegeId(),
                request.getCareerId());
        return new ResponseEntity<AuthenticationResponse>(registeredStudent, HttpStatus.CREATED);
    }

    // URL: http://localhost:8080/api/v1/account/login
    // Method: POST
    @Transactional
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse loggedStudent = authentificationService.login(request);
        return new ResponseEntity<AuthenticationResponse>(loggedStudent, HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        authentificationService.refreshToken(request, response);
    }

    private void validateStudent(Student student) {
        if (student.getName() == null || student.getName().isEmpty()) {
            throw new ValidationException("El nombre del estudiante no puede ser vacio");
        }
        if (student.getLastName() == null || student.getLastName().isEmpty()) {
            throw new ValidationException("El apellido del estudiante no puede ser vacio");
        }
        if (student.getDni() == null || student.getDni().isEmpty()) {
            throw new ValidationException("El dni del estudiante no puede ser vacio");
        } else if (student.getDni().length() != 8) {
            throw new ValidationException("El dni debe tener 8 caracteres");
        }
        if (student.getUniversityEmail() == null || student.getUniversityEmail().isEmpty()) {
            throw new ValidationException("El email de la universidad del estudiante no puede ser vacio");
        }
        // Validación de la contraseña
        if (student.getPassword().length() < 8) {
            throw new ValidationException("La contraseña del estudiante debe tener al menos 8 caracteres");
        }

        // Validación de campos únicos
        if (studentService.getStudentByEmail(student.getEmail()) != null) {
            throw new ValidationException("El email del estudiante ya está en uso");
        }
        if (studentService.getStudentByUniversityEmail(student.getUniversityEmail()) != null) {
            throw new ValidationException("El email de la universidad del estudiante ya está en uso");
        }
        if (studentService.getStudentByDni(student.getDni()) != null) {
            throw new ValidationException("El DNI del estudiante ya está en uso");
        }

    }
}
