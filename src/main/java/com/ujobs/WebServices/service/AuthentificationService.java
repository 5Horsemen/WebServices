package com.ujobs.WebServices.service;

import java.io.IOException;

import com.ujobs.WebServices.model.Student;
import com.ujobs.WebServices.response.AuthenticationResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthentificationService {
    public abstract AuthenticationResponse registerStudent(Student student, Long collegeId, Long careerId);

    public abstract AuthenticationResponse login(AuthenticationRequest authenticationRequest);

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException;
}
