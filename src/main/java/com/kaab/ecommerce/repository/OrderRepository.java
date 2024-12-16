package com.kaab.ecommerce.repository;

import com.kaab.ecommerce.entity.Order;
import com.kaab.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
