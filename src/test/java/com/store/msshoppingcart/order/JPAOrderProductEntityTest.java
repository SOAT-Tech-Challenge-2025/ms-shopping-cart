package com.store.msshoppingcart.order;

import com.store.msshoppingcart.order.infrastructure.adapters.out.model.JPAOrderEntity;
import com.store.msshoppingcart.order.infrastructure.adapters.out.model.JPAOrderProductEntity;
import com.store.msshoppingcart.order.infrastructure.adapters.out.model.JPAOrderProductId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JPAOrderProductEntityTest {

    @Test
    @DisplayName("Deve inicializar todos os campos via construtor com argumentos")
    void constructor_ShouldSetAllFields() {
        // Arrange
        JPAOrderProductId id = new JPAOrderProductId("ORD-1", 10L);
        JPAOrderEntity order = new JPAOrderEntity();
        order.setId("ORD-1");
        Integer quantity = 5;
        Double value = 50.0;

        // Act
        JPAOrderProductEntity entity = new JPAOrderProductEntity(id, order, quantity, value);

        // Assert
        assertAll("Validando integridade da entidade de item de pedido",
                () -> assertEquals(id, entity.getId()),
                () -> assertEquals(order, entity.getOrder()),
                () -> assertEquals(quantity, entity.getQtItem()),
                () -> assertEquals(value, entity.getVlQtItem()),
                () -> assertEquals(10L, entity.getProductId()),
                () -> assertEquals("ORD-1", entity.getOrderId())
        );
    }

    @Test
    @DisplayName("setProductId: Deve instanciar o ID composto se estiver nulo e definir o produto")
    void setProductId_ShouldInitializeIdIfNull() {
        // Arrange
        JPAOrderProductEntity entity = new JPAOrderProductEntity();
        Long productId = 99L;

        // Act
        entity.setProductId(productId);

        // Assert
        assertNotNull(entity.getId());
        assertEquals(productId, entity.getProductId());
    }

    @Test
    @DisplayName("setOrderId: Deve instanciar o ID composto se estiver nulo e definir o pedido")
    void setOrderId_ShouldInitializeIdIfNull() {
        // Arrange
        JPAOrderProductEntity entity = new JPAOrderProductEntity();
        String orderId = "ORD-ABC";

        // Act
        entity.setOrderId(orderId);

        // Assert
        assertNotNull(entity.getId());
        assertEquals(orderId, entity.getOrderId());
    }

    @Test
    @DisplayName("Setters: Deve permitir atualizar quantidade e valor")
    void setters_ShouldUpdateFields() {
        // Arrange
        JPAOrderProductEntity entity = new JPAOrderProductEntity();

        // Act
        entity.setQtItem(10);
        entity.setVlQtItem(100.0);

        // Assert
        assertEquals(10, entity.getQtItem());
        assertEquals(100.0, entity.getVlQtItem());
    }

    @Test
    @DisplayName("ToString: Deve formatar os dados incluindo o ID do pedido vinculado")
    void toString_ShouldContainData() {
        // Arrange
        JPAOrderEntity order = new JPAOrderEntity();
        order.setId("ORD-TEST");
        JPAOrderProductEntity entity = new JPAOrderProductEntity(null, order, 1, 10.0);

        // Act
        String result = entity.toString();

        // Assert
        assertTrue(result.contains("qtItem=1"));
        assertTrue(result.contains("vlQtItem=10.0"));
        assertTrue(result.contains("orderId=ORD-TEST"));
    }
}