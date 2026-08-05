package com.ktxfood.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDetail {
    private String foodId;
    private String foodName;
    private double price;
    private int quantity;

    public OrderDetail() {
    }

    public OrderDetail(String foodId, String foodName, double price, int quantity) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.price = price;
        this.quantity = quantity;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Thành tiền = đơn giá x số lượng (mục 2.3 yêu cầu)
    @com.fasterxml.jackson.annotation.JsonIgnore
    public double getSubtotal() {
        return price * quantity;
    }
}