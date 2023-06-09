package com.ujobs.WebServices.model;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.JoinColumn;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class College {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String collegeName;

  @ManyToMany
  @JoinTable(name = "college_career", joinColumns = @JoinColumn(name = "college_id"), inverseJoinColumns = @JoinColumn(name = "career_id"))
  private List<Career> careers;
}
