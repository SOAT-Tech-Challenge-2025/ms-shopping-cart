package com.store.msshoppingcart.product;

import com.store.msshoppingcart.product.infrastructure.adapters.in.dto.ProductRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductRequestDTOTest {

    @Test
    @DisplayName("Deve inicializar todos os campos via construtor com argumentos")
    void constructor_ShouldSetAllFields() {
        // Arrange
        String name = "Hambúrguer";
        Long categoryId = 1L;
        BigDecimal price = new BigDecimal("25.00");
        Long time = 15L;

        // Act
        ProductRequestDTO dto = new ProductRequestDTO(name, categoryId, price, time);

        // Assert
        assertAll("Validando integridade do ProductRequestDTO",
                () -> assertEquals(name, dto.getProductName()),
                () -> assertEquals(categoryId, dto.getIdCategory()),
                () -> assertEquals(price, dto.getUnitPrice()),
                () -> assertEquals(time, dto.getPreparationTime())
        );
    }

    @Test
    @DisplayName("Setters: Deve permitir atualizar os dados da requisição")
    void setters_ShouldUpdateFields() {
        // Arrange
        ProductRequestDTO dto = new ProductRequestDTO();
        String newName = "Pizza";
        BigDecimal newPrice = new BigDecimal("45.00");

        // Act
        dto.setProductName(newName);
        dto.setUnitPrice(newPrice);
        dto.setIdCategory(2L);
        dto.setPreparationTime(20L);

        // Assert
        assertEquals(newName, dto.getProductName());
        assertEquals(newPrice, dto.getUnitPrice());
        assertEquals(2L, dto.getIdCategory());
        assertEquals(20L, dto.getPreparationTime());
    }

    @Test
    @DisplayName("ToString: Deve formatar os campos da requisição corretamente")
    void toString_ShouldContainFields() {
        // Arrange
        ProductRequestDTO dto = new ProductRequestDTO("Suco", 3L, new BigDecimal("10.0"), 5L);

        // Act
        String result = dto.toString();

        // Assert
        assertTrue(result.contains("productName='Suco'"));
        assertTrue(result.contains("idCategory=3"));
        assertTrue(result.contains("unitPrice=10.0"));
    }

    @Test
    @DisplayName("Deve aceitar valores nulos (flexibilidade para desserialização JSON)")
    void shouldHandleNullValues() {
        // Act
        ProductRequestDTO dto = new ProductRequestDTO(null, null, null, null);

        // Assert
        assertNull(dto.getProductName());
        assertNull(dto.getUnitPrice());
    }
}