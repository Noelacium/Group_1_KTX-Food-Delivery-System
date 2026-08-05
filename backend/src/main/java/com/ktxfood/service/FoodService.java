package com.ktxfood.service;

import com.ktxfood.dto.FoodRequest;
import com.ktxfood.model.Food;
import com.ktxfood.repository.FoodRepository;
import com.ktxfood.utils.IdGenerator;
import com.ktxfood.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    public List<Food> searchByName(String keyword) {
        return foodRepository.findByNameContaining(keyword);
    }

    public Food getFoodById(String id) {
        return foodRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy món: " + id));
    }

    // ===== Các chức năng dành cho admin =====

    public Food createFood(FoodRequest request) {
        ValidationUtils.validateNotEmpty(request.getName(), "Tên món");
        ValidationUtils.validatePrice(request.getPrice());

        Food food = new Food(
                IdGenerator.generateFoodId(),
                request.getName(),
                request.getPrice(),
                request.getCategory(),
                request.isAvailable(),
                request.getQuantity()
        );
        foodRepository.save(food);
        return food;
    }

    public Food updateFood(String id, FoodRequest request) {
        Food food = getFoodById(id);
        ValidationUtils.validateNotEmpty(request.getName(), "Tên món");
        ValidationUtils.validatePrice(request.getPrice());

        food.setName(request.getName());
        food.setPrice(request.getPrice());
        food.setCategory(request.getCategory());
        food.setAvailable(request.isAvailable());
        food.setQuantity(request.getQuantity());

        foodRepository.save(food);
        return food;
    }

    public void deleteFood(String id) {
        getFoodById(id); // ném lỗi nếu không tồn tại
        foodRepository.deleteById(id);
    }

    // Cập nhật riêng số lượng món (không cần sửa toàn bộ thông tin)
    public Food updateQuantity(String id, int quantity) {
        Food food = getFoodById(id);
        food.setQuantity(quantity);
        food.setAvailable(quantity > 0); // hết số lượng thì tự động chuyển hết hàng
        foodRepository.save(food);
        return food;
    }
}