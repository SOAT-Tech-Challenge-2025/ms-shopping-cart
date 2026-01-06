package com.store.msshoppingcart.order.infrastructure.adapters.in.dto;

public class UserAuthResponseDTO {
    private String user;
    private String role;

    public UserAuthResponseDTO() {}

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}