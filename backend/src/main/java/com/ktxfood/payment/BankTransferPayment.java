package com.ktxfood.payment;

import com.ktxfood.model.Order;

public class BankTransferPayment implements PaymentMethod {

    @Override
    public boolean pay(Order order) {
        // Giả lập xác nhận chuyển khoản thành công
        return true;
    }

    @Override
    public String getMethodName() {
        return "Chuyển khoản";
    }
}