package com.ujobs.WebServices.requests;

import com.ujobs.WebServices.model.Employer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployerRegistrationRequest {
    private Employer employer;
}
