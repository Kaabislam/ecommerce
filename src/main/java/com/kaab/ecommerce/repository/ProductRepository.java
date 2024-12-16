package com.kaab.ecommerce.repository;

import com.kaab.ecommerce.entity.Product;
import com.kaab.ecommerce.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
