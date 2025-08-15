package com.example.order_service.model;

public enum OrderStatus {
    PENDING,        // Orden recibida, pendiente de pago
    PAID,           // Pago confirmado
    PROCESSING,     // En preparación
    SHIPPED,        // Enviado
    IN_TRANSIT,     // En tránsito
    DELIVERED,      // Entregado
    CANCELLED,      // Cancelado
    REFUNDED        // Reembolsado
}
