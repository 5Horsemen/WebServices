package com.ujobs.WebServices.requests;

import com.ujobs.WebServices.model.Student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentRegistrationRequest {
    private Student student;
    private Long collegeId;
    private Long careerId;
}
