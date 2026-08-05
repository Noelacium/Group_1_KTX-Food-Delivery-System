package com.ktxfood.payment;

import com.ktxfood.model.Order;
import com.ktxfood.model.Student;
import com.ktxfood.exception.InsufficientBalanceException;

public class WalletPayment implements PaymentMethod {

    @Override
    public boolean pay(Order order) {
        Student student = order.getStudent();
        double total = order.getTotalAmount();

        if (student.getBalance() < total) {
            throw new InsufficientBalanceException("Số dư không đủ để thanh toán đơn hàng " + order.getOrderId());
        }

        student.setBalance(student.getBalance() - total);
        return true;
    }

    @Override
    public String getMethodName() {
        return "Ví sinh viên";
    }
}