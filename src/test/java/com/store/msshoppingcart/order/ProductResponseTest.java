package com.store.msshoppingcart.order;

import com.store.msshoppingcart.order.infrastructure.adapters.in.dto.ProductResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductResponseTest {

    @Test
    @DisplayName("Deve inicializar todos os campos via construtor com argumentos")
    void constructor_ShouldSetAllFields() {
        // Arrange
        Long productId = 100L;
        Integer quantity = 5;
        Double unitPrice = 12.50;

        // Act
        ProductResponse response = new ProductResponse(productId, quantity, unitPrice);

        // Assert
        assertAll("Validando integridade do ProductResponse",
                () -> assertEquals(productId, response.getProductId()),
                () -> assertEquals(quantity, response.getQuantity()),
                () -> assertEquals(unitPrice, response.getVlUnitProduct())
        );
    }

    @Test
    @DisplayName("Setters: Deve permitir atualizar os dados do produto")
    void setters_ShouldUpdateFields() {
        // Arrange
        ProductResponse response = new ProductResponse();
        Long newId = 200L;
        Integer newQty = 10;
        Double newPrice = 50.0;

        // Act
        response.setProductId(newId);
        response.setQuantity(newQty);
        response.setVlUnitProduct(newPrice);

        // Assert
        assertEquals(newId, response.getProductId());
        assertEquals(newQty, response.getQuantity());
        assertEquals(newPrice, response.getVlUnitProduct());
    }

    @Test
    @DisplayName("Deve aceitar valores nulos (flexibilidade para mapeadores)")
    void shouldHandleNullValues() {
        // Act
        ProductResponse response = new ProductResponse(null, null, null);

        // Assert
        assertNull(response.getProductId());
        assertNull(response.getQuantity());
        assertNull(response.getVlUnitProduct());
    }
}
