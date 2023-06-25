package com.ujobs.WebServices.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAndStudentDto {
    private String universityEmail;
    private String college;
    private String career;
}
