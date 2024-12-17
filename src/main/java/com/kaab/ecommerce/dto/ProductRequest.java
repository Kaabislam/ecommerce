package com.kaab.ecommerce.dto;

import com.kaab.ecommerce.entity.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class ProductRequest {
    @Valid
    private Product product;
    @Min(value = 0, message = "Initial stock must be a non-negative value")
    private Integer initialStock;
}
