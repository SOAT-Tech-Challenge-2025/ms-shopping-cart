package com.store.msshoppingcart.category.testes;

import com.store.msshoppingcart.category.infrastructure.adapters.in.dto.CategoryResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryResponseDTOTest {

    @Test
    @DisplayName("Deve inicializar a mensagem corretamente via construtor")
    void constructor_ShouldSetMessage() {
        // Arrange
        String message = "Operação realizada com sucesso";

        // Act
        CategoryResponseDTO dto = new CategoryResponseDTO(message);

        // Assert
        assertEquals(message, dto.getMessage());
    }

    @Test
    @DisplayName("Builder: Deve retornar uma nova instância do DTO")
    void builder_ShouldReturnNewInstance() {
        // Act
        CategoryResponseDTO dto = CategoryResponseDTO.builder();

        // Assert
        assertNotNull(dto);
        assertNull(dto.getMessage());
    }

    @Test
    @DisplayName("Setter: Deve permitir alterar a mensagem")
    void setter_ShouldUpdateMessage() {
        // Arrange
        CategoryResponseDTO dto = new CategoryResponseDTO();
        String newMessage = "Nova mensagem";

        // Act
        dto.setMessage(newMessage);

        // Assert
        assertEquals(newMessage, dto.getMessage());
    }

    @Test
    @DisplayName("Equals e HashCode: Devem considerar mensagens iguais como objetos iguais")
    void equalsAndHashCode_ShouldBeConsistent() {
        // Arrange
        CategoryResponseDTO dto1 = new CategoryResponseDTO("Sucesso");
        CategoryResponseDTO dto2 = new CategoryResponseDTO("Sucesso");
        CategoryResponseDTO dto3 = new CategoryResponseDTO("Erro");

        // Assert
        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    @DisplayName("ToString: Deve conter o prefixo e a mensagem formatada")
    void toString_ShouldFormatCorrectly() {
        // Arrange
        CategoryResponseDTO dto = new CategoryResponseDTO("Teste");

        // Act
        String result = dto.toString();

        // Assert
        assertTrue(result.contains("CategoryUpdateResponseDTO"));
        assertTrue(result.contains("message='Teste'"));
    }
}