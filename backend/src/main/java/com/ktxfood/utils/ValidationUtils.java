package com.ktxfood.utils;

import com.ktxfood.exception.EmptyCartException;
import com.ktxfood.exception.InvalidQuantityException;
import com.ktxfood.exception.OutOfStockException;
import com.ktxfood.model.Cart;
import com.ktxfood.model.Food;

public class ValidationUtils {

    // Không cho phép nhập giá tiền âm
    public static void validatePrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Giá tiền không được âm");
        }
    }

    // Không cho phép số lượng món nhỏ hơn 1
    public static void validateQuantity(int quantity) {
        if (quantity < 1) {
            throw new InvalidQuantityException("Số lượng phải lớn hơn hoặc bằng 1");
        }
    }

    // Không cho phép đặt món đã hết hàng
    public static void validateFoodAvailable(Food food) {
        if (!food.isAvailable()) {
            throw new OutOfStockException("Món \"" + food.getName() + "\" hiện đã hết hàng");
        }
    }

    // Không cho phép giỏ hàng rỗng khi đặt món
    public static void validateCartNotEmpty(Cart cart) {
        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new EmptyCartException("Giỏ hàng đang trống, không thể đặt hàng");
        }
    }

    // Kiểm tra chuỗi không rỗng
    public static void validateNotEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " không được để trống");
        }
    }

    // Mã sinh viên phải gồm đúng 8 chữ số
    public static void validateStudentId(String studentId) {
        if (studentId == null || !studentId.matches("\\d{8}")) {
            throw new IllegalArgumentException("Mã sinh viên phải gồm đúng 8 chữ số");
        }
    }

    // Kiểm tra số điện thoại cơ bản (10 số)
    public static void validatePhoneNumber(String phone) {
        if (phone == null || !phone.matches("\\d{10}")) {
            throw new IllegalArgumentException("Số điện thoại không hợp lệ");
        }
    }
}