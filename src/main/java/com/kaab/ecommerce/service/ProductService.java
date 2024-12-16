package com.kaab.ecommerce.service;

import com.kaab.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

public interface ProductService {
    Product addProduct(Product product);
    Page<Product> getProducts(int page, int size, String sort);
}
