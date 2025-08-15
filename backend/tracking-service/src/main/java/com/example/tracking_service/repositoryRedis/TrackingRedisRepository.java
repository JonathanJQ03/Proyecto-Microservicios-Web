package com.example.tracking_service.repositoryRedis;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.tracking_service.model.TrackingInfo;

@Repository
public interface TrackingRedisRepository extends CrudRepository<TrackingInfo, String> {
    // Ahora usamos String porque la clave será "pedidoId:clientId"
}
