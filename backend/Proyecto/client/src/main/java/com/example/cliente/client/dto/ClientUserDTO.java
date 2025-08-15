package com.example.cliente.client.dto;
import com.example.cliente.client.model.Client;

public class ClientUserDTO {
    private Long clientId;
    private String clientName;
    private String clientEmail;
    private String userUsername;
    private String userEmail;
    private String userRole;

    public ClientUserDTO(Client client) {
        this.clientId = client.getId();
        this.clientName = client.getName();
        this.clientEmail = client.getEmail();
        this.userUsername = client.getAuthUser().getUsername();
        this.userEmail = client.getAuthUser().getEmail();
        this.userRole = client.getAuthUser().getRole().name();

    }

    public Long getClientId() {
    return clientId;
}

public void setClientId(Long clientId) {
    this.clientId = clientId;
}

public String getClientName() {
    return clientName;
}

public void setClientName(String clientName) {
    this.clientName = clientName;
}

public String getClientEmail() {
    return clientEmail;
}

public void setClientEmail(String clientEmail) {
    this.clientEmail = clientEmail;
}

public String getUserUsername() {
    return userUsername;
}

public void setUserUsername(String userUsername) {
    this.userUsername = userUsername;
}

public String getUserEmail() {
    return userEmail;
}

public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
}

public String getUserRole() {
    return userRole;
}

public void setUserRole(String userRole) {
    this.userRole = userRole;
}

}
