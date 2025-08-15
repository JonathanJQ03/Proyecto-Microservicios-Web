package com.example.cliente.client.model;

// Importa tu enum Role del paquete correcto
import com.example.cliente.client.model.Role;

import jakarta.persistence.*;

@Entity
@Table(name = "_user") // o "auth_users", según tu base de datos
public class User {

    @Id
    private Long id; // mismo id que viene del auth-service

    private String username;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role; // enum de tu proyecto

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}
