package com.ujobs.WebServices.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollegeDto {
    private Long id;
    private String collegeName;
    private List<String> careerNames;
}
