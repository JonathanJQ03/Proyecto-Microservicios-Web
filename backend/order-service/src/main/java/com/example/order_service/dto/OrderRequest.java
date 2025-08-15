package com.example.order_service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @NotEmpty(message = "At least one order item is required")
    private List<@Valid OrderItemRequest> items;

    private String shippingAddress;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemRequest {
        @NotEmpty(message = "Product ID is required")
        private String productId;

        @NotEmpty(message = "Product name is required")
        private String productName;

        @NotNull(message = "Quantity is required")
        @PositiveOrZero(message = "Quantity must be positive or zero")
        private Integer quantity;

        @NotNull(message = "Unit price is required")
        @PositiveOrZero(message = "Unit price must be positive or zero")
        private BigDecimal unitPrice;
    }
}
