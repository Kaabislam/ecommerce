package com.kaab.ecommerce.service;

import com.kaab.ecommerce.dto.OrderRequest;
import com.kaab.ecommerce.entity.Order;

public interface OrderService {
    Order createOrder(OrderRequest orderRequest);
    Order getOrderById(Integer id);
}
