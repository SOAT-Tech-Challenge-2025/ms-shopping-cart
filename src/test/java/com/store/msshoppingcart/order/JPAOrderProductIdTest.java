package com.store.msshoppingcart.order;

import com.store.msshoppingcart.order.infrastructure.adapters.out.model.JPAOrderProductId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JPAOrderProductIdTest {

    @Test
    @DisplayName("Deve inicializar os campos corretamente via construtor")
    void constructor_ShouldSetFields() {
        // Arrange
        String orderId = "ORD-123";
        Long productId = 50L;

        // Act
        JPAOrderProductId id = new JPAOrderProductId(orderId, productId);

        // Assert
        assertEquals(orderId, id.getId());
        assertEquals(productId, id.getProductId());
    }

    @Test
    @DisplayName("Setters: Deve permitir atualizar os componentes da chave")
    void setters_ShouldUpdateFields() {
        // Arrange
        JPAOrderProductId id = new JPAOrderProductId();

        // Act
        id.setId("ORD-999");
        id.setProductId(100L);

        // Assert
        assertEquals("ORD-999", id.getId());
        assertEquals(100L, id.getProductId());
    }

    @Test
    @DisplayName("Equals e HashCode: Deve garantir a unicidade baseada nos dois campos")
    void equalsAndHashCode_ShouldBeConsistent() {
        // Arrange
        JPAOrderProductId id1 = new JPAOrderProductId("A", 1L);
        JPAOrderProductId id2 = new JPAOrderProductId("A", 1L);
        JPAOrderProductId id3 = new JPAOrderProductId("A", 2L);
        JPAOrderProductId id4 = new JPAOrderProductId("B", 1L);

        // Assert
        assertAll("Verificando contrato de chave composta",
                () -> assertEquals(id1, id2, "Devem ser iguais com mesmos valores"),
                () -> assertEquals(id1.hashCode(), id2.hashCode(), "HashCodes devem ser idênticos"),
                () -> assertNotEquals(id1, id3, "Devem ser diferentes se o produto mudar"),
                () -> assertNotEquals(id1, id4, "Devem ser diferentes se o pedido mudar"),
                () -> assertNotEquals(id1, new Object(), "Não deve ser igual a tipos diferentes")
        );
    }

    @Test
    @DisplayName("Deve lidar com campos nulos no equals sem lançar exceção")
    void equals_ShouldHandleNulls() {
        // Arrange
        JPAOrderProductId id1 = new JPAOrderProductId(null, null);
        JPAOrderProductId id2 = new JPAOrderProductId(null, null);

        // Assert
        assertEquals(id1, id2);
        assertNotEquals(id1, new JPAOrderProductId("A", 1L));
    }
}
