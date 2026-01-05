package com.store.msshoppingcart.product;

import com.store.msshoppingcart.product.domain.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    @DisplayName("Deve inicializar todos os campos via construtor e gerar datas automaticamente")
    void constructor_ShouldInitializeFieldsAndDates() {
        // Arrange
        String name = "Hambúrguer";
        Long categoryId = 1L;
        BigDecimal price = new BigDecimal("25.50");
        Long prepTime = 15L;

        // Act
        Product product = new Product(name, categoryId, price, prepTime);

        // Assert
        assertAll("Validando integridade do modelo Product",
                () -> assertEquals(name, product.getProductName()),
                () -> assertEquals(categoryId, product.getIdCategory()),
                () -> assertEquals(price, product.getUnitPrice()),
                () -> assertEquals(prepTime, product.getPreparationTime()),
                () -> assertNotNull(product.getDateInclusion(), "Data de inclusão deve ser gerada"),
                () -> assertNotNull(product.getTimestamp(), "Timestamp deve ser gerado")
        );
    }

    @Test
    @DisplayName("Setters: Deve permitir atualizar os dados do produto")
    void setters_ShouldUpdateFields() {
        // Arrange
        Product product = new Product();
        String newName = "Pizza";
        BigDecimal newPrice = new BigDecimal("40.0");

        // Act
        product.setProductName(newName);
        product.setUnitPrice(newPrice);

        // Assert
        assertEquals(newName, product.getProductName());
        assertEquals(newPrice, product.getUnitPrice());
    }

    @Test
    @DisplayName("ToString: Deve conter as informações principais do produto")
    void toString_ShouldContainProductInfo() {
        // Arrange
        Product product = new Product("Sushi", 2L, new BigDecimal("60.0"), 30L);

        // Act
        String result = product.toString();

        // Assert
        assertTrue(result.contains("productName='Sushi'"));
        assertTrue(result.contains("unitPrice=60.0"));
        assertTrue(result.contains("preparationTime=30"));
    }

    @Test
    @DisplayName("Auditoria: Deve permitir definir manualmente datas e timestamps")
    void auditSetters_ShouldWork() {
        // Arrange
        Product product = new Product();
        Date customDate = new Date(System.currentTimeMillis());
        Timestamp customTs = new Timestamp(System.currentTimeMillis());

        // Act
        product.setDateInclusion(customDate);
        product.setTimestamp(customTs);

        // Assert
        assertEquals(customDate, product.getDateInclusion());
        assertEquals(customTs, product.getTimestamp());
    }
}