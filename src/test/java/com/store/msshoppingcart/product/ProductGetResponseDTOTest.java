package com.store.msshoppingcart.product;

import com.store.msshoppingcart.product.infrastructure.adapters.in.dto.ProductGetResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductGetResponseDTOTest {

    @Test
    @DisplayName("Deve inicializar todos os campos via construtor com argumentos")
    void constructor_ShouldSetAllFields() {
        // Arrange
        Long id = 1L;
        String name = "Hambúrguer";
        Long categoryId = 10L;
        BigDecimal price = new BigDecimal("25.50");
        Long prepTime = 15L;

        // Act
        ProductGetResponseDTO dto = new ProductGetResponseDTO(id, name, categoryId, price, prepTime);

        // Assert
        assertAll("Validando integridade do ProductGetResponseDTO",
                () -> assertEquals(id, dto.getId()),
                () -> assertEquals(name, dto.getNameProduct()),
                () -> assertEquals(categoryId, dto.getIdCategory()),
                () -> assertEquals(price, dto.getUnitPrice()),
                () -> assertEquals(prepTime, dto.getPreparationTime())
        );
    }

    @Test
    @DisplayName("Setters: Deve permitir atualizar os dados do produto")
    void setters_ShouldUpdateFields() {
        // Arrange
        ProductGetResponseDTO dto = new ProductGetResponseDTO();
        Long newId = 50L;
        String newName = "Pizza";
        BigDecimal newPrice = new BigDecimal("40.00");

        // Act
        dto.setId(newId);
        dto.setNameProduct(newName);
        dto.setUnitPrice(newPrice);

        // Assert
        assertEquals(newId, dto.getId());
        assertEquals(newName, dto.getNameProduct());
        assertEquals(newPrice, dto.getUnitPrice());
    }

    @Test
    @DisplayName("ToString: Deve conter as informações principais do produto")
    void toString_ShouldContainData() {
        // Arrange
        ProductGetResponseDTO dto = new ProductGetResponseDTO(1L, "Suco", 2L, new BigDecimal("10.0"), 5L);

        // Act
        String result = dto.toString();

        // Assert
        assertTrue(result.contains("id=1"));
        assertTrue(result.contains("nameProduct='Suco'"));
        assertTrue(result.contains("unitPrice=10.0"));
    }

    @Test
    @DisplayName("Deve aceitar valores nulos sem lançar exceções")
    void shouldHandleNullValues() {
        // Act
        ProductGetResponseDTO dto = new ProductGetResponseDTO(null, null, null, null, null);

        // Assert
        assertNull(dto.getId());
        assertNull(dto.getNameProduct());
        assertNull(dto.getUnitPrice());
    }
}