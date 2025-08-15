package com.example.tracking_service.repositoryJPA;

import java.util.Optional;

import com.example.tracking_service.model.TrackingInfo;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface TrackingJPARepository extends JpaRepository<TrackingInfo, Long> {
  Optional<TrackingInfo> findByPedidoId(Long pedidoId);
}
