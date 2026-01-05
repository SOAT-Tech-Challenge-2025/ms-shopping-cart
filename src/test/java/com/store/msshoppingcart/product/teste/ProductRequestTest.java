package com.store.msshoppingcart.product.teste;

import com.store.msshoppingcart.order.infrastructure.adapters.in.dto.ProductRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductRequestTest {

    @Test
    @DisplayName("Deve inicializar todos os campos via construtor com argumentos")
    void constructor_ShouldSetAllFields() {
        // Arrange
        Long productId = 10L;
        Double price = 25.50;
        Integer prepTime = 15;

        // Act
        ProductRequest request = new ProductRequest(productId, price, prepTime);

        // Assert
        assertAll("Validando integridade do ProductRequest",
                () -> assertEquals(productId, request.getProductId()),
                () -> assertEquals(price, request.getVlUnitProduct()),
                () -> assertEquals(prepTime, request.getPreparationTime())
        );
    }

    @Test
    @DisplayName("Setters: Deve permitir atualizar os dados do item do produto")
    void setters_ShouldUpdateFields() {
        // Arrange
        ProductRequest request = new ProductRequest();
        Long newId = 50L;
        Double newPrice = 10.0;
        Integer newTime = 5;

        // Act
        request.setProductId(newId);
        request.setVlUnitProduct(newPrice);
        request.setPreparationTime(newTime);

        // Assert
        assertEquals(newId, request.getProductId());
        assertEquals(newPrice, request.getVlUnitProduct());
        assertEquals(newTime, request.getPreparationTime());
    }

    @Test
    @DisplayName("Deve aceitar valores nulos sem lançar exceções (flexibilidade para desserialização)")
    void shouldHandleNullValues() {
        // Act
        ProductRequest request = new ProductRequest(null, null, null);

        // Assert
        assertNull(request.getProductId());
        assertNull(request.getVlUnitProduct());
        assertNull(request.getPreparationTime());
    }
}
