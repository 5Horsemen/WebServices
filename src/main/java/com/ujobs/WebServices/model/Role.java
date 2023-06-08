package com.ujobs.WebServices.model;

import lombok.Getter;

@Getter
public enum Role {
  STUDENT("Student"),
  EMPLOYER("Employer");

  private final String displayName;

  Role(String displayName) {
    this.displayName = displayName;
  }
}
