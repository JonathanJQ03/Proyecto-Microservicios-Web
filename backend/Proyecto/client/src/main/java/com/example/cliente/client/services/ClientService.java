package com.example.cliente.client.services;


import java.util.List;
import java.util.Optional;

import com.example.cliente.client.dto.ClientUserDTO;
import com.example.cliente.client.model.Client;

public interface ClientService {
    List <Client> getAllClients();
    Optional <Client> getClientById(Long id);
    //Este opcional nos sirve para manejar casos donde el cliente no exista
    //y evitar excepciones innecesarias
    Client createClient(Client client);
    Client updateClient(Long id, Client client);
    void deleteClient(Long id);
List<ClientUserDTO> getAllClientUserDTOs();

}
