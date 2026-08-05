package com.ktxfood.dto;

import java.util.List;

public class OrderRequest {
    private String studentId;
    private List<OrderItemRequest> items;

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public List<OrderItemRequest> getItems() { return items; }
    public void setItems(List<OrderItemRequest> items) { this.items = items; }
}