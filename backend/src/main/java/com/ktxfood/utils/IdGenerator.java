package com.ktxfood.utils;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class IdGenerator {

    // Sinh mã đơn hàng dạng: ORD-xxxxxxxx
    public static String generateOrderId() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // Sinh mã sinh viên gồm đúng 8 chữ số, VD: 20210001
    public static String generateStudentId() {
        int number = ThreadLocalRandom.current().nextInt(0, 100_000_000); // 0 - 99999999
        return String.format("%08d", number);
    }

    // Sinh mã món ăn dạng: FD-xxxxxxxx
    public static String generateFoodId() {
        return "FD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}