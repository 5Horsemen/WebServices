package com.ujobs.WebServices.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ujobs.WebServices.model.Career;
import com.ujobs.WebServices.model.College;
import com.ujobs.WebServices.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByCollege(College college);

    List<Student> findByCareer(Career career);

    Student findByEmail(String email);

    List<Student> findByCollegeAndCareer(College college, Career career);

    Student findByUniversityEmail(String universityEmail);

    List<Student> findByName(String name);

    List<Student> findByLastName(String lastName);

    Student findByDni(String dni);

}
