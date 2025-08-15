package com.example.order_service.dto;

public class ClientUserDTO {
    private String clientId;
    private String clientName;
    private String clientEmail;
    private String userUsername;
    private String userEmail;
    private String userRole;

    // Getters y setters
    public String getClientId() { return clientId; }
public void setClientId(String clientId) { this.clientId = clientId; }
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
}

