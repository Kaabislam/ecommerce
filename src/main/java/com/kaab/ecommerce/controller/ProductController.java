package com.kaab.ecommerce.controller;

import com.kaab.ecommerce.dto.ProductRequest;
import com.kaab.ecommerce.entity.Product;
import com.kaab.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/products")
@Validated
@Tag(name = "Product Controller", description = "APIs for managing products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Add a new product (admin only)
    @PostMapping
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Product> addProduct(@Valid @RequestBody ProductRequest productRequest) {
        Product createdProduct = productService.addProduct(productRequest.getProduct(),productRequest.getInitialStock());
        return ResponseEntity.ok(createdProduct);
    }

    // List all products with pagination and sorting
    @GetMapping
    public ResponseEntity<Page<Product>> listProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String sort
    ) {
        return ResponseEntity.ok(productService.getProducts(page, size, sort));
    }
}
