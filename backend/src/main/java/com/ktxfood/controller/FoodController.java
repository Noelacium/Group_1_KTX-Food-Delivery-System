package com.ktxfood.controller;

import com.ktxfood.dto.FoodRequest;
import com.ktxfood.dto.QuantityRequest;
import com.ktxfood.model.Food;
import com.ktxfood.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @GetMapping
    public List<Food> getAllFoods() {
        return foodService.getAllFoods();
    }

    @GetMapping("/search")
    public List<Food> searchFoods(@RequestParam String name) {
        return foodService.searchByName(name);
    }

    @GetMapping("/{id}")
    public Food getFoodById(@PathVariable String id) {
        return foodService.getFoodById(id);
    }

    // ===== Endpoint dành cho admin =====

    @PostMapping
    public Food createFood(@RequestBody FoodRequest request) {
        return foodService.createFood(request);
    }

    @PutMapping("/{id}")
    public Food updateFood(@PathVariable String id, @RequestBody FoodRequest request) {
        return foodService.updateFood(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteFood(@PathVariable String id) {
        foodService.deleteFood(id);
    }

    @PatchMapping("/{id}/quantity")
    public Food updateQuantity(@PathVariable String id, @RequestBody QuantityRequest request) {
        return foodService.updateQuantity(id, request.getQuantity());
    }
}