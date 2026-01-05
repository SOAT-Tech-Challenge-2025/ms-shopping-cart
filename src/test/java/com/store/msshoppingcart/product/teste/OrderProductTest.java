package com.store.msshoppingcart.product.teste;

import com.store.msshoppingcart.order.domain.model.OrderProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderProductTest {

    @Test
    @DisplayName("Deve inicializar todos os campos via construtor com argumentos")
    void constructor_ShouldSetAllFields() {
        // Arrange
        String orderId = "ORD-1";
        Long productId = 10L;
        Integer quantity = 2;
        Double totalValue = 25.0;

        // Act
        OrderProduct item = new OrderProduct(orderId, productId, quantity, totalValue);

        // Assert
        assertAll("Validando integridade do item do pedido",
                () -> assertEquals(orderId, item.getId()),
                () -> assertEquals(productId, item.getProductId()),
                () -> assertEquals(quantity, item.getQtItem()),
                () -> assertEquals(totalValue, item.getVlQtItem())
        );
    }

    @Test
    @DisplayName("Setters: Deve permitir atualizar os dados do item")
    void setters_ShouldUpdateFields() {
        // Arrange
        OrderProduct item = new OrderProduct();
        Long newProductId = 50L;
        Integer newQuantity = 10;

        // Act
        item.setProductId(newProductId);
        item.setQtItem(newQuantity);

        // Assert
        assertEquals(newProductId, item.getProductId());
        assertEquals(newQuantity, item.getQtItem());
    }

    @Test
    @DisplayName("Equals e HashCode: Devem considerar itens com mesmos valores como iguais")
    void equalsAndHashCode_ShouldBeConsistent() {
        // Arrange
        OrderProduct item1 = new OrderProduct("ORD-1", 1L, 1, 10.0);
        OrderProduct item2 = new OrderProduct("ORD-1", 1L, 1, 10.0);
        OrderProduct item3 = new OrderProduct("ORD-1", 2L, 1, 10.0);

        // Assert
        assertEquals(item1, item2, "Itens idênticos devem ser iguais");
        assertNotEquals(item1, item3, "Itens com produtos diferentes devem ser desiguais");
        assertEquals(item1.hashCode(), item2.hashCode());
    }

    @Test
    @DisplayName("ToString: Deve conter as propriedades do item formatadas")
    void toString_ShouldContainItemData() {
        // Arrange
        OrderProduct item = new OrderProduct("ID-123", 99L, 5, 100.0);

        // Act
        String result = item.toString();

        // Assert
        assertTrue(result.contains("id=ID-123"));
        assertTrue(result.contains("productId=99"));
        assertTrue(result.contains("qtItem=5"));
        assertTrue(result.contains("vlQtItem=100.0"));
    }
}
