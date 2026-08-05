package com.ktxfood.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
    private String orderId;
    private Student student;
    private List<OrderDetail> items;
    private LocalDateTime orderTime;
    private OrderStatus status;

    public Order() {
        this.items = new ArrayList<>();
        this.status = OrderStatus.PENDING;
    }

    public Order(String orderId, Student student, List<OrderDetail> items) {
        this.orderId = orderId;
        this.student = student;
        this.items = items;
        this.orderTime = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<OrderDetail> getItems() {
        return items;
    }

    public void setItems(List<OrderDetail> items) {
        this.items = items;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    // Tổng tiền đơn hàng
    @com.fasterxml.jackson.annotation.JsonIgnore
    public double getTotalAmount() {
        double total = 0;
        for (OrderDetail detail : items) {
            total += detail.getSubtotal();
        }
        return total;
    }
}