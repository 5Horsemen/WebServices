package com.ujobs.WebServices.service;

import java.util.List;

import com.ujobs.WebServices.dto.StudentDto;
import com.ujobs.WebServices.model.Student;

public interface StudentService {

    public abstract StudentDto getStudentById(Long id);

    public abstract Student getStudentByDni(String dni);

    public abstract Student getStudentByEmail(String email);

    public abstract Student getStudentByUniversityEmail(String universityEmail);

    public abstract List<Student> getStudentsByName(String name);

    public abstract List<Student> getStudentsByLastName(String lastName);

    public abstract List<Student> getStudentsByCollege(String collegeName);

    public abstract List<Student> getStudentsByCareer(String career);

    public abstract List<Student> getStudentsByCollegeAndCareer(String collegeName, String careerNam);

    public abstract Student updateStudent(Student student);

    public abstract void deleteStudent(Student student);

    public abstract Student changeStudentPassword(Long studentId, String newPassword);
}
