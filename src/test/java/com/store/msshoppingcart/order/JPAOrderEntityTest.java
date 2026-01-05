package com.store.msshoppingcart.order;

import com.store.msshoppingcart.order.infrastructure.adapters.out.model.JPAOrderEntity;
import com.store.msshoppingcart.order.infrastructure.adapters.out.model.JPAOrderProductEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JPAOrderEntityTest {

    @Test
    @DisplayName("Deve inicializar todos os campos através do construtor com argumentos")
    void constructor_ShouldSetAllFields() {
        // Arrange
        String id = "ORD-123";
        double amount = 250.0;
        Integer minutes = 45;
        String clientId = "CLIENT-001";
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        List<JPAOrderProductEntity> produtos = new ArrayList<>();

        // Act
        JPAOrderEntity entity = new JPAOrderEntity(id, amount, minutes, clientId, now, produtos);

        // Assert
        assertAll("Validando integridade da entidade JPA Order",
                () -> assertEquals(id, entity.getId()),
                () -> assertEquals(amount, entity.getTotalAmountOrder()),
                () -> assertEquals(minutes, entity.getMinute()),
                () -> assertEquals(clientId, entity.getClientId()),
                () -> assertEquals(now, entity.getTimestamp()),
                () -> assertEquals(produtos, entity.getProdutos())
        );
    }

    @Test
    @DisplayName("Setters: Deve permitir atualizar os dados da entidade para persistência")
    void setters_ShouldUpdateFields() {
        // Arrange
        JPAOrderEntity entity = new JPAOrderEntity();
        String newId = "ORD-999";
        double newAmount = 50.0;
        List<JPAOrderProductEntity> newProducts = new ArrayList<>();

        // Act
        entity.setId(newId);
        entity.setTotalAmountOrder(newAmount);
        entity.setProdutos(newProducts);

        // Assert
        assertEquals(newId, entity.getId());
        assertEquals(newAmount, entity.getTotalAmountOrder());
        assertEquals(newProducts, entity.getProdutos());
    }

    @Test
    @DisplayName("ToString: Deve conter as informações formatadas e a contagem de produtos")
    void toString_ShouldContainEntityInfo() {
        // Arrange
        JPAOrderEntity entity = new JPAOrderEntity();
        entity.setId("ORD-1");
        entity.setTotalAmountOrder(10.5);
        entity.setProdutos(new ArrayList<>());

        // Act
        String result = entity.toString();

        // Assert
        assertTrue(result.contains("id='ORD-1'"));
        assertTrue(result.contains("totalAmountOrder=10.5"));
        assertTrue(result.contains("produtos=0"), "O toString deve mostrar o tamanho da lista de produtos");
    }

    @Test
    @DisplayName("Relacionamento: Deve lidar com lista de produtos nula no toString")
    void toString_ShouldHandleNullProducts() {
        // Arrange
        JPAOrderEntity entity = new JPAOrderEntity();
        entity.setProdutos(null);

        // Act & Assert
        assertDoesNotThrow(entity::toString);
        assertTrue(entity.toString().contains("produtos=0"));
    }
}