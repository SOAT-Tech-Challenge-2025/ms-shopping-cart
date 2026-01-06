package com.store.msshoppingcart.order;

import com.store.msshoppingcart.order.domain.model.Order;
import com.store.msshoppingcart.order.domain.model.OrderProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    @DisplayName("Deve inicializar todos os campos via construtor e gerar timestamp automaticamente")
    void constructor_ShouldInitializeFields() {
        // Arrange
        String id = "ORD-123";
        Double total = 150.50;
        Integer minutes = 30;
        String clientId = "CLI-001";
        List<OrderProduct> products = new ArrayList<>();

        // Act
        Order order = new Order(id, total, minutes, clientId, products);

        // Assert
        assertAll("Validando campos do construtor",
                () -> assertEquals(id, order.getId()),
                () -> assertEquals(total, order.getTotalAmountOrder()),
                () -> assertEquals(minutes, order.getMinute()),
                () -> assertEquals(products, order.getOrderProducts()),
                () -> assertNotNull(order.getTimestamp(), "O timestamp deve ser gerado automaticamente")
        );
    }

    @Test
    @DisplayName("Setters: Deve permitir atualizar os dados do pedido")
    void setters_ShouldUpdateFields() {
        // Arrange
        Order order = new Order();
        String newId = "ORD-999";
        Double newTotal = 50.0;

        // Act
        order.setId(newId);
        order.setTotalAmountOrder(newTotal);

        // Assert
        assertEquals(newId, order.getId());
        assertEquals(newTotal, order.getTotalAmountOrder());
    }

    @Test
    @DisplayName("Equals e HashCode: Devem ser baseados no estado do objeto")
    void equalsAndHashCode_ShouldBeConsistent() {
        // Arrange
        Order order1 = new Order("1", 10.0, 5, "C1", null);
        Order order2 = new Order("1", 10.0, 5, "C1", null);

        // Forçamos o mesmo timestamp para o teste de igualdade ser exato
        order2.setTimestamp(order1.getTimestamp());

        // Assert
        assertEquals(order1, order2);
        assertEquals(order1.hashCode(), order2.hashCode());
        assertNotEquals(order1, new Order("2", 10.0, 5, "C1", null));
    }

    @Test
    @DisplayName("ToString: Deve conter as informações principais do pedido")
    void toString_ShouldContainOrderInfo() {
        // Arrange
        Order order = new Order("ORD-1", 10.0, 5, "CLI-1", null);

        // Act
        String result = order.toString();

        // Assert
        assertTrue(result.contains("id=ORD-1"));
        assertTrue(result.contains("totalAmountOrder=10.0"));
        assertTrue(result.contains("clientId=CLI-1"));
    }
}