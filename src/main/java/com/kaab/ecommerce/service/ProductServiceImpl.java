package com.kaab.ecommerce.service;

import com.kaab.ecommerce.entity.Product;
import com.kaab.ecommerce.exception.BadRequestException;
import com.kaab.ecommerce.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl  implements ProductService{
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product addProduct(Product product) {
        if (product == null ) {
            throw new BadRequestException("Invalid product request.");
        }
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
