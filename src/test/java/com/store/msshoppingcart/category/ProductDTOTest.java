package com.store.msshoppingcart.category;

import com.store.msshoppingcart.category.infrastructure.adapters.in.dto.ProductDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class ProductDTOTest {

    @Test
    @DisplayName("Deve inicializar todos os campos via construtor com argumentos")
    void constructor_ShouldSetAllFields() {
        // Arrange
        Long id = 100L;
        String name = "Café Espresso";
        BigDecimal price = new BigDecimal("7.50");
        Integer time = 5;
        Date inclusionDate = new Date(System.currentTimeMillis());

        // Act
        ProductDTO dto = new ProductDTO(id, name, price, time, inclusionDate);

        // Assert
        assertAll("Verificando integridade do DTO",
                () -> assertEquals(id, dto.getProductId()),
                () -> assertEquals(name, dto.getNameProduct()),
                () -> assertEquals(price, dto.getUnitPrice()),
                () -> assertEquals(time, dto.getPreparationTime()),
                () -> assertEquals(inclusionDate, dto.getDtInclusion())
        );
    }

    @Test
    @DisplayName("Deve permitir modificar valores através dos setters")
    void setters_ShouldUpdateFields() {
        // Arrange
        ProductDTO dto = new ProductDTO();
        String newName = "Capuccino";
        BigDecimal newPrice = new BigDecimal("12.00");

        // Act
        dto.setNameProduct(newName);
        dto.setUnitPrice(newPrice);
        dto.setProductId(1L);

        // Assert
        assertEquals(newName, dto.getNameProduct());
        assertEquals(newPrice, dto.getUnitPrice());
        assertEquals(1L, dto.getProductId());
    }

    @Test
    @DisplayName("Deve aceitar valores nulos sem lançar exceções")
    void shouldHandleNullValues() {
        // Act
        ProductDTO dto = new ProductDTO(null, null, null, null, null);

        // Assert
        assertNull(dto.getProductId());
        assertNull(dto.getNameProduct());
        assertNull(dto.getUnitPrice());
    }
}