package com.example.tracking_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tracking_service.model.TrackingInfo;
import com.example.tracking_service.repositoryJPA.TrackingJPARepository;
import com.example.tracking_service.repositoryRedis.TrackingRedisRepository;

@Service
public class TrackingService {

    @Autowired
    private TrackingJPARepository trackingJpaRepository;

    @Autowired
    private TrackingRedisRepository trackingRedisRepository;

    public TrackingInfo registrar(TrackingInfo info) {
    // Construir la clave Redis
    info.setPedidoKey(info.getPedidoId() + ":" + info.getClientId());

    // Guardar en MySQL
    TrackingInfo savedJpa = trackingJpaRepository.save(info);

    // Guardar en Redis
    trackingRedisRepository.save(savedJpa);

    return savedJpa;
}

    public TrackingInfo obtenerPorPedidoId(Long pedidoId, String clientId) {
        // Construir la clave Redis
        String key = pedidoId + ":" + clientId;

        // Leer desde Redis
        return trackingRedisRepository.findById(key)
                .orElseThrow(() -> new RuntimeException("No encontrado en Redis o no autorizado"));
    }
}
