package com.store.msshoppingcart.product;

import com.store.msshoppingcart.product.infrastructure.adapters.out.model.ProductEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class ProductEntityTest {

    @Test
    @DisplayName("Getters e Setters: Deve permitir a manipulação de todos os campos da entidade")
    void gettersAndSetters_ShouldWorkCorrectly() {
        // Arrange
        ProductEntity entity = new ProductEntity();
        Long id = 1L;
        String name = "Hambúrguer";
        BigDecimal price = new BigDecimal("25.00");
        Integer time = 15;
        Date date = new Date(System.currentTimeMillis());
        Timestamp ts = new Timestamp(System.currentTimeMillis());

        // Act
        entity.setId(id);
        entity.setProductName(name);
        entity.setUnitPrice(price);
        entity.setPreparationTime(time);
        entity.setInclusionDate(date);
        entity.setTimestamp(ts);

        // Assert
        assertAll("Validando integridade da entidade JPA Product",
                () -> assertEquals(id, entity.getId()),
                () -> assertEquals(name, entity.getProductName()),
                () -> assertEquals(price, entity.getUnitPrice()),
                () -> assertEquals(time, entity.getPreparationTime()),
                () -> assertEquals(date, entity.getInclusionDate()),
                () -> assertEquals(ts, entity.getTimestamp())
        );
    }

    @Test
    @DisplayName("Equals e HashCode: Devem considerar apenas o ID para identidade do objeto")
    void equalsAndHashCode_ShouldBeBasedOnId() {
        // Arrange
        ProductEntity p1 = new ProductEntity(); p1.setId(1L);
        ProductEntity p2 = new ProductEntity(); p2.setId(1L);
        ProductEntity p3 = new ProductEntity(); p3.setId(2L);

        // Assert
        assertAll("Verificando contrato de igualdade JPA",
                () -> assertEquals(p1, p2, "Produtos com mesmo ID devem ser iguais"),
                () -> assertEquals(p1.hashCode(), p2.hashCode(), "HashCodes devem ser idênticos para mesmo ID"),
                () -> assertNotEquals(p1, p3, "Produtos com IDs diferentes devem ser desiguais"),
                () -> assertNotEquals(p1, null)
        );
    }

    @Test
    @DisplayName("ToString: Deve conter as informações vitais da entidade")
    void toString_ShouldContainFields() {
        // Arrange
        ProductEntity entity = new ProductEntity();
        entity.setId(10L);
        entity.setProductName("Pizza");

        // Act
        String result = entity.toString();

        // Assert
        assertTrue(result.contains("id=10"));
        assertTrue(result.contains("productName='Pizza'"));
        assertTrue(result.contains("ProductEntity"));
    }
}