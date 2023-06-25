package com.ujobs.WebServices.dto;

import com.ujobs.WebServices.model.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private Role role;
    private String profileImage;
    private UserAndEmployerDto employer;
    private UserAndStudentDto student;
}
