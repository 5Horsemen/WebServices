package com.ujobs.WebServices.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ujobs.WebServices.dto.UserDto;
import com.ujobs.WebServices.exception.ResourceNotFoundException;
import com.ujobs.WebServices.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    // URL: http://localhost:8080/api/v1/user/{id}
    // Method: GET
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(value = "id") Long id) {
        UserDto userDto = userService.getUserById(id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/v1/user/filterByEmail
    // Method: GET
    @GetMapping("/filterByEmail")
    public ResponseEntity<UserDto> getUserByEmail(@RequestParam(value = "email") String email) {
        UserDto userDto = userService.getUserByEmail(email);
        if (userDto == null) {
            throw new ResourceNotFoundException("User not found with email: " + email);
        }
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/v1/user/{id}/profileImage
    // Method: POST
    @PostMapping("/{id}/profileImage")
    public ResponseEntity<UserDto> uploadProfileImage(
            @PathVariable(value = "id") Long userId,
            @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            // Verificar si se ha seleccionado un archivo
            if (!imageFile.isEmpty()) {
                String imageData = Base64.getEncoder().encodeToString(imageFile.getBytes());
                UserDto userDto = userService.uploadProfileImage(userId, imageData);
                return new ResponseEntity<>(userDto, HttpStatus.OK);
            } else {
                throw new IllegalArgumentException("No se ha seleccionado ninguna imagen");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar la imagen");
        }
    }

    // URL: http://localhost:8080/api/v1/user/search
    // Method: GET
    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> searchUsers(@RequestParam(value = "term") String term) {
        List<UserDto> users = userService.searchUsers(term);
        return ResponseEntity.ok(users);
    }

}
