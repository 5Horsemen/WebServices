package com.ujobs.WebServices.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CareerDto {
    private Long id;
    private String careerName;
    private List<Long> collegeIds;
}
