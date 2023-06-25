package com.ujobs.WebServices.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAndEmployerDto {
    private String companyName;
    private String ruc;
    private String jobPosition;
}
