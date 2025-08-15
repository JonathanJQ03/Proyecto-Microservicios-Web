package com.example.cliente.client.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.cliente.client.model.Client;

public interface ClientsRepository extends CrudRepository<Client, Long> {
   // Verifica si ya existe un cliente para un usuario específico
    boolean existsByAuthUser_Id(Long authUserId);

    // Obtener todos los clientes con sus usuarios
    @Query("SELECT c FROM Client c JOIN FETCH c.authUser")
    List<Client> findAllWithUsers();

    

}
