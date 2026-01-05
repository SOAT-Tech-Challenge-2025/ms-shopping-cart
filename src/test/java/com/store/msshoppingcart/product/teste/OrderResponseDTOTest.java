package com.store.msshoppingcart.product.teste;

import com.store.msshoppingcart.order.infrastructure.adapters.in.dto.OrderResponseDTO;
import com.store.msshoppingcart.order.infrastructure.adapters.in.dto.ProductResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderResponseDTOTest {

    @Test
    @DisplayName("Deve inicializar todos os campos via construtor com argumentos")
    void constructor_ShouldSetAllFields() {
        // Arrange
        String orderId = "ORD-123";
        Double total = 150.0;
        Integer prepTime = 45;
        String clientId = "CLI-001";
        List<ProductResponse> products = new ArrayList<>();
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        // Act
        OrderResponseDTO dto = new OrderResponseDTO(orderId, total, prepTime, clientId, products, now);

        // Assert
        assertAll("Validando integridade do DTO de resposta",
                () -> assertEquals(orderId, dto.getOrderId()),
                () -> assertEquals(total, dto.getTotalOrder()),
                () -> assertEquals(prepTime, dto.getPreparationTime()),
                () -> assertEquals(clientId, dto.getClientId()),
                () -> assertEquals(products, dto.getProducts()),
                () -> assertEquals(now, dto.getTimestamp())
        );
    }

    @Test
    @DisplayName("Setters: Deve permitir atualizar os dados da resposta")
    void setters_ShouldUpdateFields() {
        // Arrange
        OrderResponseDTO dto = new OrderResponseDTO();
        String newOrderId = "ORD-999";
        Double newTotal = 50.0;

        // Act
        dto.setOrderId(newOrderId);
        dto.setTotalOrder(newTotal);

        // Assert
        assertEquals(newOrderId, dto.getOrderId());
        assertEquals(newTotal, dto.getTotalOrder());
    }

    @Test
    @DisplayName("Equals e HashCode: Devem ser consistentes com o estado do objeto")
    void equalsAndHashCode_ShouldBeConsistent() {
        // Arrange
        Timestamp time = Timestamp.valueOf(LocalDateTime.now());
        List<ProductResponse> prodList = new ArrayList<>();

        OrderResponseDTO dto1 = new OrderResponseDTO("1", 10.0, 5, "C1", prodList, time);
        OrderResponseDTO dto2 = new OrderResponseDTO("1", 10.0, 5, "C1", prodList, time);
        OrderResponseDTO dto3 = new OrderResponseDTO("2", 10.0, 5, "C1", prodList, time);

        // Assert
        assertEquals(dto1, dto2, "DTOs com mesmos valores devem ser iguais");
        assertNotEquals(dto1, dto3, "DTOs com IDs diferentes devem ser desiguais");
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    @DisplayName("ToString: Deve conter o nome da classe e dados principais")
    void toString_ShouldContainData() {
        // Arrange
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setOrderId("ORD-TEST");

        // Act
        String result = dto.toString();

        // Assert
        assertTrue(result.contains("OrderGetResponseDTO"));
        assertTrue(result.contains("orderId=ORD-TEST"));
    }
}
