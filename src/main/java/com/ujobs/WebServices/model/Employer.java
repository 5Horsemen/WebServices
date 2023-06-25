package com.ujobs.WebServices.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employer extends User {
  @Column(name = "company_name", nullable = false)
  private String companyName;
  @Column(name = "ruc", nullable = false)
  private String ruc;
  @Column(name = "job_position", nullable = false)
  private String jobPosition;
}
