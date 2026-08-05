package com.ktxfood.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.ktxfood.model.Student;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepository {

    private static final String FILE_PATH = "src/main/resources/data/students.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Student> findAll() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists() || file.length() == 0) {
                return new ArrayList<>();
            }
            CollectionType listType = objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, Student.class);
            return objectMapper.readValue(file, listType);
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi đọc file students.json: " + e.getMessage());
        }
    }

    public Optional<Student> findById(String id) {
        return findAll().stream()
                .filter(student -> student.getId().equals(id))
                .findFirst();
    }

    public void saveAll(List<Student> students) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(FILE_PATH), students);
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi ghi file students.json: " + e.getMessage());
        }
    }

    // Thêm 1 sinh viên mới, rồi ghi lại toàn bộ file
    public void save(Student student) {
        List<Student> students = findAll();
        students.removeIf(s -> s.getId().equals(student.getId())); // tránh trùng id
        students.add(student);
        saveAll(students);
    }

    // Cập nhật số dư sau khi thanh toán ví
    public void updateBalance(String studentId, double newBalance) {
        List<Student> students = findAll();
        for (Student s : students) {
            if (s.getId().equals(studentId)) {
                s.setBalance(newBalance);
                break;
            }
        }
        saveAll(students);
    }
}