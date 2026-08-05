package com.ktxfood.controller;

import com.ktxfood.dto.PaymentRequest;
import com.ktxfood.model.Order;
import com.ktxfood.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class PaymentController {

    @Autowired
    private OrderService orderService;

    // Xác nhận thanh toán cho 1 đơn hàng đã tạo
    @PostMapping("/{id}/pay")
    public Order payOrder(@PathVariable String id, @RequestBody PaymentRequest request) {
        return orderService.checkout(id, request.getMethod());
    }
}