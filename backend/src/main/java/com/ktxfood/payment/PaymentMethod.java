package com.ktxfood.payment;

import com.ktxfood.model.Order;

public interface PaymentMethod {
    boolean pay(Order order);
    String getMethodName();
}