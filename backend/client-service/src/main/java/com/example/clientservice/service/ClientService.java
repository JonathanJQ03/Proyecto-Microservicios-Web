package com.example.clientservice.service;

import com.example.clientservice.dto.ClientRequest;
import com.example.clientservice.dto.ClientResponse;
import com.example.clientservice.exception.ResourceAlreadyExistsException;
import com.example.clientservice.exception.ResourceNotFoundException;
import com.example.clientservice.model.Client;
import com.example.clientservice.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    @Transactional
    public ClientResponse createClient(ClientRequest request) {
        // Verificar si ya existe un cliente con el mismo email o authUserId
        if (clientRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Client with email " + request.getEmail() + " already exists");
        }
        
        if (clientRepository.existsByAuthUserId(request.getAuthUserId())) {
            throw new ResourceAlreadyExistsException("Client with auth user id " + request.getAuthUserId() + " already exists");
        }

        // Crear y guardar el nuevo cliente
        Client client = Client.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .authUserId(request.getAuthUserId())
                .status(Client.ClientStatus.ACTIVE)
                .build();

        Client savedClient = clientRepository.save(client);
        return ClientResponse.fromEntity(savedClient);
    }

    @Transactional(readOnly = true)
    public ClientResponse getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
        return ClientResponse.fromEntity(client);
    }

    @Transactional(readOnly = true)
    public ClientResponse getClientByAuthUserId(String authUserId) {
        Client client = clientRepository.findByAuthUserId(authUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with auth user id: " + authUserId));
        return ClientResponse.fromEntity(client);
    }

    @Transactional(readOnly = true)
    public List<ClientResponse> getAllClients() {
        return clientRepository.findAll().stream()
                .map(ClientResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public ClientResponse updateClient(Long id, ClientRequest request) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));

        // Verificar si el email ya está en uso por otro cliente
        if (!client.getEmail().equals(request.getEmail()) && 
            clientRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Email " + request.getEmail() + " is already in use");
        }

        // Actualizar los datos del cliente
        client.setFirstName(request.getFirstName());
        client.setLastName(request.getLastName());
        client.setEmail(request.getEmail());
        client.setPhone(request.getPhone());
        client.setAddress(request.getAddress());

        Client updatedClient = clientRepository.save(client);
        return ClientResponse.fromEntity(updatedClient);
    }

    @Transactional
    public void deleteClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
        
        // En lugar de eliminar, marcamos como INACTIVO (borrado lógico)
        client.setStatus(Client.ClientStatus.INACTIVE);
        clientRepository.save(client);
    }

    @Transactional
    public void activateClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
        
        client.setStatus(Client.ClientStatus.ACTIVE);
        clientRepository.save(client);
    }
}
