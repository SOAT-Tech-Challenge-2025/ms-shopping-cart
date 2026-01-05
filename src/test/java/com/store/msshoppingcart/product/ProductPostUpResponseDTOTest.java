package com.store.msshoppingcart.product;

import com.store.msshoppingcart.product.infrastructure.adapters.in.dto.ProductPostUpResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductPostUpResponseDTOTest {

    @Test
    @DisplayName("Deve inicializar a mensagem corretamente via construtor")
    void constructor_ShouldSetMessage() {
        // Arrange
        String message = "Operação realizada com sucesso";

        // Act
        ProductPostUpResponseDTO dto = new ProductPostUpResponseDTO(message);

        // Assert
        assertEquals(message, dto.getMessage());
    }

    @Test
    @DisplayName("Builder: Deve retornar uma nova instância do DTO")
    void builder_ShouldReturnNewInstance() {
        // Act
        ProductPostUpResponseDTO dto = ProductPostUpResponseDTO.builder();

        // Assert
        assertNotNull(dto);
        assertNull(dto.getMessage());
    }

    @Test
    @DisplayName("Setter: Deve permitir atualizar a mensagem")
    void setter_ShouldUpdateMessage() {
        // Arrange
        ProductPostUpResponseDTO dto = new ProductPostUpResponseDTO();
        String newMessage = "Produto atualizado";

        // Act
        dto.setMessage(newMessage);

        // Assert
        assertEquals(newMessage, dto.getMessage());
    }

    @Test
    @DisplayName("Equals e HashCode: Devem considerar mensagens iguais como objetos iguais")
    void equalsAndHashCode_ShouldBeConsistent() {
        // Arrange
        ProductPostUpResponseDTO dto1 = new ProductPostUpResponseDTO("Sucesso");
        ProductPostUpResponseDTO dto2 = new ProductPostUpResponseDTO("Sucesso");
        ProductPostUpResponseDTO dto3 = new ProductPostUpResponseDTO("Erro");

        // Assert
        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    @DisplayName("ToString: Deve conter o prefixo e a mensagem formatada")
    void toString_ShouldFormatCorrectly() {
        // Arrange
        ProductPostUpResponseDTO dto = new ProductPostUpResponseDTO("Teste");

        // Act
        String result = dto.toString();

        // Assert
        assertTrue(result.contains("ProductUpdateResponseDTO"));
        assertTrue(result.contains("message='Teste'"));
    }
}