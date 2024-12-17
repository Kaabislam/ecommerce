package com.kaab.ecommerce.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaab.ecommerce.dto.OrderRequest;
import com.kaab.ecommerce.dto.OrderRequestItem;
import com.kaab.ecommerce.entity.Inventory;
import com.kaab.ecommerce.entity.Order;
import com.kaab.ecommerce.entity.OrderItem;
import com.kaab.ecommerce.entity.Product;
import com.kaab.ecommerce.exception.BadRequestException;
import com.kaab.ecommerce.exception.OrderNotFoundException;
import com.kaab.ecommerce.repository.InventoryRepository;
import com.kaab.ecommerce.repository.OrderItemRepository;
import com.kaab.ecommerce.repository.OrderRepository;
import com.kaab.ecommerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final String topic = "orders";
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final ObjectMapper objectMapper;
    private final OrderItemRepository orderItemRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, InventoryRepository inventoryRepository,
                            ObjectMapper objectMapper, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
        this.objectMapper = objectMapper;
        this.orderItemRepository = orderItemRepository;
    }
    @Override
    @Transactional
    public Order createOrder(OrderRequest orderRequest) {
        if (orderRequest == null || orderRequest.getItems() == null) {
            throw new BadRequestException("Invalid order request.");
        }

        // Step 1: Create a new Order object and initialize its basic attributes
        Order order = new Order();
        order.setUser(orderRequest.getUserInfo());
        order.setShippingAddress(orderRequest.getShippingAddress());
        order.setStatus("CREATED");

        // Step 2: Save the Order to generate an ID
        Order savedOrder = orderRepository.save(order);

        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0;

        // Step 3: Iterate through each item in the request
        for (OrderRequestItem requestItem : orderRequest.getItems()) {
            Product product = productRepository.findById(requestItem.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + requestItem.getProductId()));

            // Step 4: Check the inventory for the stock level using InventoryRepository
            Inventory inventory = inventoryRepository.findByProductId(requestItem.getProductId().longValue())
                    .orElseThrow(() -> new RuntimeException("Inventory not found for product: " + requestItem.getProductId()));

            // Step 5: Validate stock
            if (inventory.getQuantity() < requestItem.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            // Step 6: Deduct the stock in the inventory
            inventory.setQuantity(inventory.getQuantity() - requestItem.getQuantity());
            inventoryRepository.save(inventory);

            // Step 7: Create an OrderItem and link it with the Order
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);  // Link OrderItem to the saved Order
            orderItem.setProduct(product);
            orderItem.setQuantity(requestItem.getQuantity());
            orderItems.add(orderItem);

            // Step 8: Calculate total amount for the order
            totalAmount += product.getPrice() * requestItem.getQuantity();
        }

        // Step 9: Save all OrderItems to the order
        orderItemRepository.saveAll(orderItems);

        // Step 10: Update the Order with totalAmount and saved OrderItems
        savedOrder.setItems(orderItems);
        savedOrder.setTotalAmount(totalAmount);

        // Step 11: Save the updated Order
        return orderRepository.save(savedOrder);
    }


    @Override
    public Order getOrderById(Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
    }
}
