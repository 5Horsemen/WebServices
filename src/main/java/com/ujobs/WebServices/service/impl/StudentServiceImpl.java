package com.ujobs.WebServices.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ujobs.WebServices.dto.StudentDto;
import com.ujobs.WebServices.dto.UserDto;
import com.ujobs.WebServices.exception.ResourceNotFoundException;
import com.ujobs.WebServices.model.Career;
import com.ujobs.WebServices.model.College;
import com.ujobs.WebServices.model.Student;
import com.ujobs.WebServices.model.User;
import com.ujobs.WebServices.repository.CareerRepository;
import com.ujobs.WebServices.repository.CollegeRepository;
import com.ujobs.WebServices.repository.StudentRepository;
import com.ujobs.WebServices.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CollegeRepository collegeRepository;

    @Autowired
    private CareerRepository careerRepository;

    @Override
    public StudentDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el estudiante con el id: " + id));
        return mapToDto(student);
    }

    @Override
    public Student getStudentByDni(String dni) {
        return studentRepository.findByDni(dni);
    }

    @Override
    public Student getStudentByUniversityEmail(String universityEmail) {
        return studentRepository.findByUniversityEmail(universityEmail);
    }

    @Override
    public List<Student> getStudentsByName(String name) {
        return studentRepository.findByName(name);
    }

    @Override
    public List<Student> getStudentsByLastName(String lastName) {
        return studentRepository.findByLastName(lastName);
    }

    @Override
    public List<Student> getStudentsByCollege(String collegeName) {
        College college = collegeRepository.findByCollegeName(collegeName);
        return studentRepository.findByCollege(college);
    }

    @Override
    public List<Student> getStudentsByCareer(String careerName) {
        Career career = careerRepository.findByCareerName(careerName);
        return studentRepository.findByCareer(career);
    }

    @Override
    public List<Student> getStudentsByCollegeAndCareer(String collegeName, String careerName) {
        College college = collegeRepository.findByCollegeName(collegeName);
        Career career = careerRepository.findByCareerName(careerName);
        return studentRepository.findByCollegeAndCareer(college, career);
    }

    @Override
    public Student updateStudent(Student student) {
        Optional<Student> existingStudent = studentRepository.findById(student.getId());
        if (existingStudent.isPresent()) {
            Student studentToUpdate = existingStudent.get();

            studentToUpdate.setName(student.getName());
            studentToUpdate.setLastName(student.getLastName());
            studentToUpdate.setEmail(student.getEmail());
            studentToUpdate.setUniversityEmail(student.getUniversityEmail());
            studentToUpdate.setCollege(student.getCollege());
            studentToUpdate.setCareer(student.getCareer());

            return studentRepository.save(studentToUpdate);
        } else {
            throw new ResourceNotFoundException("No se encontro el estudiante con el id: " + student.getId());
        }
    }

    @Override
    public void deleteStudent(Student student) {
        studentRepository.delete(student);
    }

    @Override
    public Student changeStudentPassword(Long studentId, String newPassword) {
        Optional<Student> existingStudent = studentRepository.findById(studentId);

        if (existingStudent.isPresent()) {
            Student studentToUpdate = existingStudent.get();
            studentToUpdate.setPassword(newPassword);

            return studentRepository.save(studentToUpdate);
        } else {
            throw new ResourceNotFoundException("No se encontro el estudiante con el id: " + studentId);
        }
    }

    @Override
    public Student getStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    private StudentDto mapToDto(Student student) {
        StudentDto studentDto = new StudentDto();
        studentDto.setUniversityEmail(student.getUniversityEmail());
        studentDto.setCollege(student.getCollege().getCollegeName());
        studentDto.setCareer(student.getCareer().getCareerName());
        studentDto.setUser(mapUserDto(student));
        return studentDto;
    }

    private UserDto mapUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        return userDto;
    }

}
