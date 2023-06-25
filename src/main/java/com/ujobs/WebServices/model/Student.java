package com.ujobs.WebServices.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Student extends User {
  @Column(name = "university_email", nullable = false, unique = true)
  private String universityEmail;

  @ManyToOne
  @JoinColumn(name = "college_id", nullable = false)
  private College college;

  @ManyToOne
  @JoinColumn(name = "career_id", nullable = false)
  private Career career;
}
