package com.ktxfood.service;

import com.ktxfood.model.Student;
import com.ktxfood.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student getStudentById(String id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sinh viên: " + id));
    }

    public Student createStudent(Student student) {
        studentRepository.save(student);
        return student;
    }
}