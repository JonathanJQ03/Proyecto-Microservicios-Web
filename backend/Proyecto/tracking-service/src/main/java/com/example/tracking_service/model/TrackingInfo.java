package com.example.tracking_service.model;

import java.time.LocalDateTime;
import org.springframework.data.redis.core.RedisHash;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
@Entity
@RedisHash("TrackingInfo")
public class TrackingInfo {

    @jakarta.persistence.Id // JPA
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // identificador para MySQL

    @org.springframework.data.annotation.Id // Redis
    private String pedidoKey; // "pedidoId:clientId"

    private Long pedidoId;
    private String clientId;
    private String estado;
    private LocalDateTime fecha;

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPedidoKey() { return pedidoKey; }
    public void setPedidoKey(String pedidoKey) { this.pedidoKey = pedidoKey; }

    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }

    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
