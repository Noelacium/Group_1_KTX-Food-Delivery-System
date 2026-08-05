package com.ktxfood.payment;

import com.ktxfood.model.Order;

public class CashPayment implements PaymentMethod {

    @Override
    public boolean pay(Order order) {
        // Tiền mặt: xác nhận thủ công khi giao hàng, không trừ ví
        return true;
    }

    @Override
    public String getMethodName() {
        return "Tiền mặt";
    }
}