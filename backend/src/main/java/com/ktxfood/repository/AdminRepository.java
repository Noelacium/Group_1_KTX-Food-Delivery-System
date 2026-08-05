package com.ktxfood.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.ktxfood.model.Admin;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AdminRepository {

    private static final String FILE_PATH = "src/main/resources/data/admins.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AdminRepository() {
        objectMapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public List<Admin> findAll() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists() || file.length() == 0) {
                return new ArrayList<>();
            }
            CollectionType listType = objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, Admin.class);
            return objectMapper.readValue(file, listType);
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi đọc file admins.json: " + e.getMessage());
        }
    }

    public Optional<Admin> findById(String id) {
        return findAll().stream()
                .filter(admin -> admin.getId().equals(id))
                .findFirst();
    }

    public void save(Admin admin) {
        List<Admin> admins = findAll();
        admins.removeIf(a -> a.getId().equals(admin.getId()));
        admins.add(admin);
        try {
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(FILE_PATH), admins);
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi ghi file admins.json: " + e.getMessage());
        }
    }
}