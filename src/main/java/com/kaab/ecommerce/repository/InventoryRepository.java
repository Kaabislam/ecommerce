package com.kaab.ecommerce.repository;

import com.kaab.ecommerce.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository  extends JpaRepository<Inventory, Long> {
    public Optional<Inventory> findByProductId(Long productId);
}
