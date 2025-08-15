package com.example.order_service.service;

import com.example.order_service.dto.OrderRequest;
import com.example.order_service.dto.OrderResponse;
import com.example.order_service.mapper.OrderMapper;
import com.example.order_service.model.Order;
import com.example.order_service.model.OrderItem;
import com.example.order_service.model.OrderStatus;
import com.example.order_service.repository.OrderRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {
        String clientId = getClientIdFromToken(); // ⚡ ahora es String

        Order order = Order.builder()
                .orderNumber(generateOrderNumber())
                .clientId(clientId) // ⚡ asignar directamente
                .status(OrderStatus.PENDING)
                .shippingAddress(orderRequest.getShippingAddress())
                .build();

        List<OrderItem> items = orderRequest.getItems().stream()
                .map(itemRequest -> OrderItem.builder()
                        .order(order)
                        .productId(itemRequest.getProductId())
                        .productName(itemRequest.getProductName())
                        .quantity(itemRequest.getQuantity())
                        .unitPrice(itemRequest.getUnitPrice())
                        .build())
                .collect(Collectors.toList());

        order.setItems(items);

        BigDecimal totalAmount = items.stream()
                .map(item -> {
                    item.calculateSubtotal();
                    return item.getSubtotal();
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        return orderMapper.toResponse(savedOrder);
    }

    public List<OrderResponse> getMyOrders() {
        String clientId = getClientIdFromToken();
        return orderRepository.findByClientId(clientId).stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }

    private String getClientIdFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return jwt.getClaimAsString("sub"); // ⚡ ahora esto puede ser "alex"
    }

    private String generateOrderNumber() {
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
        return "ORD-" + uuid;
    }
    
}
