package com.store.msshoppingcart.order;

import com.store.msshoppingcart.order.infrastructure.adapters.in.dto.OrderSNSDTO;
import com.store.msshoppingcart.order.infrastructure.adapters.in.dto.ProductSNS;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderSNSDTOTest {

    @Test
    @DisplayName("Deve inicializar todos os campos via construtor com argumentos")
    void constructor_ShouldSetAllFields() {
        // Arrange
        String orderId = "ORD-SNS-123";
        Double total = 500.0;
        List<ProductSNS> products = new ArrayList<>();
        products.add(new ProductSNS());

        // Act
        OrderSNSDTO dto = new OrderSNSDTO(orderId, total, products);

        // Assert
        assertAll("Validando integridade do DTO SNS",
                () -> assertEquals(orderId, dto.getOrder_id()),
                () -> assertEquals(total, dto.getTotal_order_value()),
                () -> assertEquals(products, dto.getProducts()),
                () -> assertEquals(1, dto.getProducts().size())
        );
    }

    @Test
    @DisplayName("Setters: Deve permitir atualizar os campos conforme nomenclatura snake_case")
    void setters_ShouldUpdateFields() {
        // Arrange
        OrderSNSDTO dto = new OrderSNSDTO();
        String newId = "ORD-999";
        Double newValue = 99.90;

        // Act
        dto.setOrder_id(newId);
        dto.setTotal_order_value(newValue);

        // Assert
        assertEquals(newId, dto.getOrder_id());
        assertEquals(newValue, dto.getTotal_order_value());
    }

    @Test
    @DisplayName("Equals e HashCode: Devem considerar o estado completo do objeto")
    void equalsAndHashCode_ShouldBeConsistent() {
        // Arrange
        List<ProductSNS> list = new ArrayList<>();
        OrderSNSDTO dto1 = new OrderSNSDTO("ID1", 10.0, list);
        OrderSNSDTO dto2 = new OrderSNSDTO("ID1", 10.0, list);
        OrderSNSDTO dto3 = new OrderSNSDTO("ID2", 10.0, list);

        // Assert
        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    @DisplayName("ToString: Deve conter as propriedades formatadas para auditoria")
    void toString_ShouldContainData() {
        // Arrange
        OrderSNSDTO dto = new OrderSNSDTO("ORD-AUDIT", 100.0, null);

        // Act
        String result = dto.toString();

        // Assert
        assertTrue(result.contains("order_id='ORD-AUDIT'"));
        assertTrue(result.contains("total_order_value=100.0"));
    }
}