package com.example.order_service.repository;

import com.example.order_service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByIdAndClientId(Long id, String clientId);
    List<Order> findByClientId(String clientId);
    // boolean existsByOrderNumber(String orderNumber);
}
