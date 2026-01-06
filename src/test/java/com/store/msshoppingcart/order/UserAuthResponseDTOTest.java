package com.store.msshoppingcart.order;

import com.store.msshoppingcart.order.infrastructure.adapters.in.dto.UserAuthResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserAuthResponseDTOTest {

    @Test
    @DisplayName("Deve testar getters e setters com sucesso")
    void testGettersAndSetters() {
        // Arrange
        UserAuthResponseDTO dto = new UserAuthResponseDTO();
        String expectedUser = "john.doe@example.com";
        String expectedRole = "ADMIN";

        // Act
        dto.setUser(expectedUser);
        dto.setRole(expectedRole);

        // Assert
        assertEquals(expectedUser, dto.getUser(), "O usuário deve ser igual ao definido no setter");
        assertEquals(expectedRole, dto.getRole(), "A role deve ser igual à definida no setter");
    }

    @Test
    @DisplayName("Deve garantir que os campos iniciam como nulos no construtor padrão")
    void testDefaultConstructor() {
        // Act
        UserAuthResponseDTO dto = new UserAuthResponseDTO();

        // Assert
        assertNull(dto.getUser(), "O usuário deve ser nulo por padrão");
        assertNull(dto.getRole(), "A role deve ser nula por padrão");
    }
}