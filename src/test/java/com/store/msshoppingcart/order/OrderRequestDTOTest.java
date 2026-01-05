package com.store.msshoppingcart.order;

import com.store.msshoppingcart.order.infrastructure.adapters.in.dto.OrderRequestDTO;
import com.store.msshoppingcart.order.infrastructure.adapters.in.dto.ProductRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderRequestDTOTest {

    @Test
    @DisplayName("Deve inicializar todos os campos via construtor com argumentos")
    void constructor_ShouldSetAllFields() {
        // Arrange
        String clientId = "CLIENT-001";
        List<ProductRequest> products = new ArrayList<>();
        products.add(new ProductRequest()); // Supondo que ProductRequest exista

        // Act
        OrderRequestDTO dto = new OrderRequestDTO(clientId, products);

        // Assert
        assertEquals(clientId, dto.getClientId());
        assertEquals(products, dto.getProducts());
        assertEquals(1, dto.getProducts().size());
    }

    @Test
    @DisplayName("Setters: Deve permitir atualizar o cliente e a lista de produtos")
    void setters_ShouldUpdateFields() {
        // Arrange
        OrderRequestDTO dto = new OrderRequestDTO();
        String newClientId = "CLIENT-999";
        List<ProductRequest> newProducts = new ArrayList<>();

        // Act
        dto.setClientId(newClientId);
        dto.setProducts(newProducts);

        // Assert
        assertEquals(newClientId, dto.getClientId());
        assertEquals(newProducts, dto.getProducts());
    }

    @Test
    @DisplayName("Deve lidar corretamente com lista de produtos nula")
    void handleNullProducts() {
        // Arrange
        OrderRequestDTO dto = new OrderRequestDTO();

        // Act
        dto.setProducts(null);

        // Assert
        assertNull(dto.getProducts());
    }
}
