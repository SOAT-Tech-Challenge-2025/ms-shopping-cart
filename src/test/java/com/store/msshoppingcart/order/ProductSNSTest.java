package com.store.msshoppingcart.order;

import com.store.msshoppingcart.order.infrastructure.adapters.in.dto.ProductSNS;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductSNSTest {

    @Test
    @DisplayName("Deve inicializar todos os campos via construtor com argumentos")
    void constructor_ShouldSetAllFields() {
        // Arrange
        String name = "Café Espresso";
        Integer qty = 3;
        Double price = 7.50;
        String category = "Bebidas Quentes";

        // Act
        ProductSNS product = new ProductSNS(name, qty, price, category);

        // Assert
        assertAll("Validando campos do ProductSNS",
                () -> assertEquals(name, product.getName()),
                () -> assertEquals(qty, product.getQuantity()),
                () -> assertEquals(price, product.getUnit_price()),
                () -> assertEquals(category, product.getCategory())
        );
    }

    @Test
    @DisplayName("Setters: Deve permitir atualizar os dados enriquecidos do produto")
    void setters_ShouldUpdateFields() {
        // Arrange
        ProductSNS product = new ProductSNS();
        String newName = "Pão de Queijo";
        Double newPrice = 5.0;

        // Act
        product.setName(newName);
        product.setUnit_price(newPrice);
        product.setQuantity(10);
        product.setCategory("Salgados");

        // Assert
        assertEquals(newName, product.getName());
        assertEquals(newPrice, product.getUnit_price());
        assertEquals(10, product.getQuantity());
        assertEquals("Salgados", product.getCategory());
    }

    @Test
    @DisplayName("Deve aceitar valores nulos (flexibilidade para mapeamento parcial)")
    void shouldHandleNullValues() {
        // Act
        ProductSNS product = new ProductSNS(null, null, null, null);

        // Assert
        assertNull(product.getName());
        assertNull(product.getUnit_price());
    }
}
