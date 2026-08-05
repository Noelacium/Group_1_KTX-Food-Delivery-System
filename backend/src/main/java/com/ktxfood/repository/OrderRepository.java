package com.ktxfood.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ktxfood.model.Order;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepository {

    private static final String FILE_PATH = "src/main/resources/data/orders.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OrderRepository() {
        // Cần module này để đọc/ghi được LocalDateTime trong Order
        objectMapper.registerModule(new JavaTimeModule());
    }

    public List<Order> findAll() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists() || file.length() == 0) {
                return new ArrayList<>();
            }
            CollectionType listType = objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, Order.class);
            return objectMapper.readValue(file, listType);
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi đọc file orders.json: " + e.getMessage());
        }
    }

    public Optional<Order> findById(String orderId) {
        return findAll().stream()
                .filter(order -> order.getOrderId().equals(orderId))
                .findFirst();
    }

    public void saveAll(List<Order> orders) {
        try {
            // Use project absolute path to ensure the data folder exists and is writable
            String absolutePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "data" + File.separator + "orders.json";
            File outFile = new File(absolutePath);
            File parent = outFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(outFile, orders);
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi ghi file orders.json: " + e.getMessage());
        }
    }

    // Lưu đơn hàng mới (dùng khi tạo order)
    public void save(Order order) {
        List<Order> orders = findAll();
        orders.removeIf(o -> o.getOrderId().equals(order.getOrderId()));
        orders.add(order);
        saveAll(orders);
    }
}