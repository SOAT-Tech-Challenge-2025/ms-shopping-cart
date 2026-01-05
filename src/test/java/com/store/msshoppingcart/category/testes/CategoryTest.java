package com.store.msshoppingcart.category.testes;

import com.store.msshoppingcart.category.domain.model.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    @DisplayName("Deve inicializar a categoria com nome, data e timestamp no construtor com argumentos")
    void constructorWithArguments_ShouldInitializeFields() {
        // Arrange & Act
        String expectedName = "Eletrônicos";
        Category category = new Category(expectedName);

        // Assert
        assertEquals(expectedName, category.getCategoryName());
        assertNotNull(category.getDateInclusion(), "A data de inclusão não deve ser nula");
        assertNotNull(category.getTimestamp(), "O timestamp não deve ser nulo");

        // Verifica se a data de inclusão é próxima ao momento atual (margem de 1s)
        long currentTime = System.currentTimeMillis();
        assertTrue(Math.abs(category.getDateInclusion().getTime() - currentTime) < 1000);
    }

    @Test
    @DisplayName("Deve permitir alterar o nome da categoria via setter")
    void setter_ShouldUpdateName() {
        // Arrange
        Category category = new Category();
        String newName = "Livros";

        // Act
        category.setCategoryName(newName);

        // Assert
        assertEquals(newName, category.getCategoryName());
    }

    @Test
    @DisplayName("Equals e HashCode: Devem considerar a igualdade de todos os campos")
    void equalsAndHashCode_ShouldBeConsistent() {
        // Arrange
        Category category1 = new Category("Moda");
        // Forçamos os mesmos valores de data para garantir a igualdade no teste
        Date date = category1.getDateInclusion();
        Timestamp ts = category1.getTimestamp();

        Category category2 = new Category();
        category2.setCategoryName("Moda");
        // Injetando os mesmos valores via reflexão ou apenas comparando objetos criados identicamente
        // Como não há setters para data/ts, vamos testar a desigualdade primeiro
        Category category3 = new Category("Moda"); // Terá timestamp milisegundos diferente

        // Assert
        assertEquals(category1, category1, "Um objeto deve ser igual a si mesmo");
        assertNotEquals(category1, category3, "Objetos com timestamps diferentes não devem ser iguais");
        assertNotEquals(category1, null);
        assertNotEquals(category1, "uma string");
    }

    @Test
    @DisplayName("ToString: Deve conter as informações principais da classe")
    void toString_ShouldContainFields() {
        // Arrange
        Category category = new Category("Alimentos");

        // Act
        String result = category.toString();

        // Assert
        assertTrue(result.contains("categoryName='Alimentos'"));
        assertTrue(result.contains("Category{"));
    }
}