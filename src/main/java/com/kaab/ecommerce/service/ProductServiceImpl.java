package com.kaab.ecommerce.service;

import com.kaab.ecommerce.dto.ProductRequest;
import com.kaab.ecommerce.entity.Inventory;
import com.kaab.ecommerce.entity.Product;
import com.kaab.ecommerce.exception.BadRequestException;
import com.kaab.ecommerce.repository.InventoryRepository;
import com.kaab.ecommerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl  implements ProductService{
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    public ProductServiceImpl(ProductRepository productRepository, InventoryRepository inventoryRepository) {
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    @Transactional
    public Product addProduct(Product product,Integer initialStock) {
        if (product == null) {
            throw new BadRequestException("Invalid product request.");
        }
        Product savedProduct = productRepository.save(product);
        Inventory inventory = Inventory.builder().
                product(savedProduct)
                .quantity(initialStock==null?0:initialStock)
                .available(initialStock > 0)
                .build();
        inventoryRepository.save(inventory);
        return productRepository.save(product);
    }

    @Override
    public Page<Product> getProducts(int page, int size, String sort) {
        String[] sortParams = sort.split(",");
        Sort.Direction direction = Sort.Direction.fromString(sortParams[1]);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortParams[0]));
        return productRepository.findAll(pageable);
    }
}
