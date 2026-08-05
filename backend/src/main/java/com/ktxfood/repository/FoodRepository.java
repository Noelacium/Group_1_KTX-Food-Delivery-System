package com.ktxfood.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.ktxfood.model.Food;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FoodRepository {

    private static final String FILE_PATH = "src/main/resources/data/foods.json";

    private final ObjectMapper objectMapper = new ObjectMapper();

    private File getFile() {
        return new File(FILE_PATH);
    }

    public List<Food> findAll() {
        try {
            File file = getFile();

            if (!file.exists() || file.length() == 0) {
                return new ArrayList<>();
            }

            CollectionType listType = objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, Food.class);

            return objectMapper.readValue(file, listType);

        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi đọc foods.json", e);
        }
    }

    public Optional<Food> findById(String id) {
        return findAll().stream()
                .filter(food -> food.getId().equals(id))
                .findFirst();
    }

    public List<Food> findByNameContaining(String keyword) {
        List<Food> result = new ArrayList<>();
        for (Food food : findAll()) {
            if (food.getName().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(food);
            }
        }
        return result;
    }

    public void saveAll(List<Food> foods) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(getFile(), foods);
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi ghi foods.json", e);
        }
    }

    public void save(Food food) {
        List<Food> foods = findAll();

        boolean found = false;

        for (int i = 0; i < foods.size(); i++) {
            if (foods.get(i).getId().equals(food.getId())) {
                foods.set(i, food);
                found = true;
                break;
            }
        }

        if (!found) {
            foods.add(food);
        }

        saveAll(foods);
    }

    public void deleteById(String id) {
        List<Food> foods = findAll();
        foods.removeIf(food -> food.getId().equals(id));
        saveAll(foods);
    }
}