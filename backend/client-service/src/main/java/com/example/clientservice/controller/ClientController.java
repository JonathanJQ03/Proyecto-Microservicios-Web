package com.example.clientservice.controller;

import com.example.clientservice.dto.ClientRequest;
import com.example.clientservice.dto.ClientResponse;
import com.example.clientservice.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ClientResponse createClient(@Valid @RequestBody ClientRequest request) {
        return clientService.createClient(request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CLIENT')")
    public ClientResponse getClientById(@PathVariable Long id, Authentication authentication) {
        checkClientAccess(id, authentication);
        return clientService.getClientById(id);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<ClientResponse> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CLIENT')")
    public ClientResponse getCurrentClient(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String authUserId = jwt.getSubject();
        return clientService.getClientByAuthUserId(authUserId);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CLIENT')")
    public ClientResponse updateClient(
            @PathVariable Long id,
            @Valid @RequestBody ClientRequest request,
            Authentication authentication) {
        checkClientAccess(id, authentication);
        return clientService.updateClient(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public void deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
    }

    @PutMapping("/{id}/activate")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void activateClient(@PathVariable Long id) {
        clientService.activateClient(id);
    }

    /**
     * Verifica si el usuario actual tiene permiso para acceder al recurso del cliente
     * Los administradores pueden acceder a cualquier recurso, los clientes solo a los suyos
     */
    private void checkClientAccess(Long clientId, Authentication authentication) {
        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return;
        }

        Jwt jwt = (Jwt) authentication.getPrincipal();
        String authUserId = jwt.getSubject();

        ClientResponse client = clientService.getClientByAuthUserId(authUserId);
        if (!client.getId().equals(clientId)) {
            throw new AccessDeniedException("No tienes permiso para acceder a este recurso");
        }
    }
}
