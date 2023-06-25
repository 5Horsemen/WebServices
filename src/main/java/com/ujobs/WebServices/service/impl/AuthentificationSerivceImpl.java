package com.ujobs.WebServices.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

import com.ujobs.WebServices.exception.ResourceNotFoundException;
import com.ujobs.WebServices.model.Career;
import com.ujobs.WebServices.model.College;
import com.ujobs.WebServices.model.Employer;
import com.ujobs.WebServices.model.Role;
import com.ujobs.WebServices.model.Student;
import com.ujobs.WebServices.model.Token;
import com.ujobs.WebServices.model.TokenType;
import com.ujobs.WebServices.model.User;
import com.ujobs.WebServices.repository.CareerRepository;
import com.ujobs.WebServices.repository.CollegeRepository;
import com.ujobs.WebServices.repository.EmployerRepository;
import com.ujobs.WebServices.repository.StudentRepository;
import com.ujobs.WebServices.repository.TokenRepository;
import com.ujobs.WebServices.repository.UserRepository;
import com.ujobs.WebServices.requests.AuthenticationRequest;
import com.ujobs.WebServices.response.AuthenticationResponse;
import com.ujobs.WebServices.service.AuthentificationService;
import com.ujobs.WebServices.service.JwtService;

@Service
@RequiredArgsConstructor
public class AuthentificationSerivceImpl implements AuthentificationService {

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CollegeRepository collegeRepository;

    @Autowired
    private CareerRepository careerRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse registerStudent(Student student, Long collegeId, Long careerId) {
        College college = collegeRepository.findById(collegeId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro el college con el id: " + collegeId));
        Career career = careerRepository.findById(careerId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro la carrera con el id: " + careerId));

        student.setCollege(college);
        student.setCareer(career);
        student.setRole(Role.STUDENT);
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        var savedStudent = studentRepository.save(student);

        var jwtToken = jwtService.generateToken(savedStudent.getId(), savedStudent);
        var refreshToken = jwtService.generateRefreshToken(savedStudent);
        saveUserToken(savedStudent, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthenticationResponse registerEmployer(Employer employer) {

        employer.setRole(Role.EMPLOYER);
        employer.setPassword(passwordEncoder.encode(employer.getPassword()));
        var savedEmployer = employerRepository.save(employer);

        var jwtToken = jwtService.generateToken(savedEmployer.getId(), savedEmployer);
        var refreshToken = jwtService.generateRefreshToken(savedEmployer);
        saveEmployerToken(savedEmployer, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail());
        var jwtToken = jwtService.generateToken(user.getId(), user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void saveEmployerToken(Employer employer, String jwtToken) {
        var token = Token.builder()
                .user(employer)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    @Override
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.studentRepository.findByEmail(userEmail);
            if (jwtService.isTokenValid(refreshToken, user)) {
                var userDetails = new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        new ArrayList<>());
                var accessToken = jwtService.generateToken(user.getId(), userDetails);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

}
