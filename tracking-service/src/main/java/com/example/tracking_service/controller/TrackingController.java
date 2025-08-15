package com.example.tracking_service.controller;

import com.example.tracking_service.model.TrackingInfo;
import com.example.tracking_service.service.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

@RestController
@RequestMapping("/api/tracking")
public class TrackingController {

    @Autowired
    private TrackingService trackingService;

    @PostMapping
    public TrackingInfo recibirTracking(@RequestBody TrackingInfo info) {
        return trackingService.registrar(info);
    }

    @GetMapping("/my-order/{pedidoId}")
public TrackingInfo verEstadoPedido(@PathVariable Long pedidoId, Authentication authentication) {
    Jwt jwt = (Jwt) authentication.getPrincipal();
    String clientId = jwt.getClaimAsString("sub");

    try {
        return trackingService.obtenerPorPedidoId(pedidoId, clientId);
    } catch (RuntimeException e) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    }
}

}
