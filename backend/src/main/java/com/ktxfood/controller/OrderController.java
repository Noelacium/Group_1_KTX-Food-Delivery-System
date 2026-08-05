package com.ktxfood.controller;

import com.ktxfood.dto.OrderRequest;
import com.ktxfood.model.Order;
import com.ktxfood.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Sinh viên xác nhận đặt hàng (chưa thanh toán)
    @PostMapping
    public Order createOrder(@RequestBody OrderRequest request) {
        return orderService.createOrder(request);
    }

    // Xem chi tiết 1 đơn hàng
    @GetMapping("/{id}")
    public Order getOrder(@PathVariable String id) {
        return orderService.getOrderById(id);
    }
}