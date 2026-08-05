package com.ktxfood.service;

import com.ktxfood.dto.OrderItemRequest;
import com.ktxfood.dto.OrderRequest;
import com.ktxfood.model.*;
import com.ktxfood.payment.BankTransferPayment;
import com.ktxfood.payment.CashPayment;
import com.ktxfood.payment.PaymentMethod;
import com.ktxfood.payment.WalletPayment;
import com.ktxfood.repository.FoodRepository;
import com.ktxfood.repository.OrderRepository;
import com.ktxfood.repository.StudentRepository;
import com.ktxfood.utils.IdGenerator;
import com.ktxfood.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private StudentRepository studentRepository;

    // Tạo đơn hàng mới: xây dựng giỏ hàng tạm để validate, rồi chuyển thành đơn hàng
    public Order createOrder(OrderRequest request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sinh viên: " + request.getStudentId()));

        // Bước 1: đưa các món vào giỏ hàng tạm, validate từng món theo business rule
        Cart cart = new Cart(student.getId());
        if (request.getItems() != null) {
            for (OrderItemRequest itemReq : request.getItems()) {
                Food food = foodRepository.findById(itemReq.getFoodId())
                        .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy món: " + itemReq.getFoodId()));

                ValidationUtils.validateQuantity(itemReq.getQuantity());
                ValidationUtils.validateFoodAvailable(food);
                // Kiểm tra tồn kho đủ để đặt số lượng yêu cầu
                if (food.getQuantity() < itemReq.getQuantity()) {
                    throw new com.ktxfood.exception.OutOfStockException("Món '" + food.getName() + "' chỉ còn " + food.getQuantity() + " suất");
                }

                cart.getItems().add(new CartItem(food.getId(), food.getName(), food.getPrice(), itemReq.getQuantity()));
            }
        }

        // Bước 2: không cho phép giỏ hàng rỗng khi đặt món (mục 5)
        ValidationUtils.validateCartNotEmpty(cart);

        // Bước 3: chốt giỏ hàng đã hợp lệ thành danh sách chi tiết đơn hàng
        List<OrderDetail> details = new ArrayList<>();
        for (CartItem item : cart.getItems()) {
            details.add(new OrderDetail(item.getFoodId(), item.getFoodName(), item.getPrice(), item.getQuantity()));
        }

        Order order = new Order(IdGenerator.generateOrderId(), student, details);
        orderRepository.save(order);
        return order;
    }

    // Xem chi tiết đơn hàng
    public Order getOrderById(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đơn hàng: " + orderId));
    }

    // Xác nhận thanh toán cho đơn hàng đã tạo
    public Order checkout(String orderId, String methodName) {
        Order order = getOrderById(orderId);

        PaymentMethod paymentMethod = resolvePaymentMethod(methodName);
        boolean success = paymentMethod.pay(order);

        if (success) {
            // Giảm tồn kho cho từng món trong đơn
            for (OrderDetail detail : order.getItems()) {
                Food food = foodRepository.findById(detail.getFoodId())
                        .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy món khi cập nhật tồn kho: " + detail.getFoodId()));
                int remaining = food.getQuantity() - detail.getQuantity();
                if (remaining < 0) {
                    throw new com.ktxfood.exception.OutOfStockException("Số lượng trong kho không đủ cho món: " + food.getName());
                }
                food.setQuantity(remaining);
                if (remaining == 0) food.setAvailable(false);
                foodRepository.save(food);
            }

            order.setStatus(OrderStatus.PAID);
            orderRepository.save(order);

            if (paymentMethod instanceof WalletPayment) {
                studentRepository.updateBalance(order.getStudent().getId(), order.getStudent().getBalance());
            }
        }

        return order;
    }

    // Polymorphism: map String -> đúng loại PaymentMethod tương ứng
    private PaymentMethod resolvePaymentMethod(String methodName) {
        return switch (methodName.toUpperCase()) {
            case "WALLET" -> new WalletPayment();
            case "CASH" -> new CashPayment();
            case "BANK_TRANSFER" -> new BankTransferPayment();
            default -> throw new IllegalArgumentException("Phương thức thanh toán không hợp lệ: " + methodName);
        };
    }
}