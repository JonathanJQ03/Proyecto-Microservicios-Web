package com.example.cliente.client.services;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.cliente.client.dto.ClientUserDTO;
import com.example.cliente.client.model.Client;
import com.example.cliente.client.repository.ClientsRepository;

@Service
@Transactional
public class ClientServiceImplement implements ClientService{

    private final ClientsRepository clientsRepository;

    public ClientServiceImplement(ClientsRepository clientsRepository) {
        this.clientsRepository = clientsRepository;
    }

    @Override
    public List<Client> getAllClients() {
        return (List<Client>)clientsRepository.findAll(); 
    }

    @Override
    public Optional<Client> getClientById(Long id) {
        return clientsRepository.findById(id);
        // Este método devuelve un Optional, que es una forma segura de manejar
        // la posibilidad de que el cliente no exista.
    }

    @Override
    public Client createClient(Client client) {
       if (client.getId() != null) {
            throw new IllegalArgumentException("New clients must not have an ID");
        }
        return clientsRepository.save(client);
        // Este método guarda un nuevo cliente en la base de datos.
        // Si el cliente ya tiene un ID, lanzamos una excepción.
    }

    @Override
    public Client updateClient(Long id, Client client) {
        if(clientsRepository.existsById(id)){
            client.setId(id);
            return clientsRepository.save(client);
        } else {
            throw new IllegalArgumentException("Client with ID " + id + " does not exist");
        }
    }

    @Override
    public void deleteClient(Long id) {
        if(clientsRepository.existsById(id)) {
            clientsRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Client with ID " + id + " does not exist");
        }
    }
 @Override
public List<ClientUserDTO> getAllClientUserDTOs() {
    return clientsRepository.findAllWithUsers()
            .stream()
            .map(ClientUserDTO::new)
            .toList(); // o .collect(Collectors.toList()) si usas Java 8
}



}
