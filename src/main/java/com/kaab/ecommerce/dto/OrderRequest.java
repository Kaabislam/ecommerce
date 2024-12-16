package com.kaab.ecommerce.dto;

import com.kaab.ecommerce.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private UserInfo userInfo;
    private List<OrderRequestItem> items;
    private String shippingAddress;

    // Getters and Setters
}