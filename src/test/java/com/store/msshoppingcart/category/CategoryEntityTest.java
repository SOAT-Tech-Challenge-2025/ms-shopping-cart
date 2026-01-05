package com.store.msshoppingcart.category;

import com.store.msshoppingcart.category.infrastructure.adapters.out.model.CategoryEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CategoryEntityTest {

    @Test
    @DisplayName("Deve inicializar todos os campos através do construtor com argumentos")
    void constructorWithArguments_ShouldSetAllFields() {
        // Arrange
        Long id = 1L;
        String name = "Bebidas";
        Date date = new Date(System.currentTimeMillis());
        Timestamp ts = Timestamp.valueOf(LocalDateTime.now());

        // Act
        CategoryEntity entity = new CategoryEntity(id, name, date, ts);

        // Assert
        assertEquals(id, entity.getId());
        assertEquals(name, entity.getCategoryName());
        assertEquals(date, entity.getDateInclusion());
        assertEquals(ts, entity.getTimestamp());
    }

    @Test
    @DisplayName("Builder: Deve retornar uma nova instância vazia")
    void builder_ShouldReturnNewInstance() {
        // Act
        CategoryEntity entity = CategoryEntity.builder();

        // Assert
        assertNotNull(entity);
        assertNull(entity.getId());
    }

    @Test
    @DisplayName("Setters: Deve permitir a alteração de todos os campos da entidade")
    void setters_ShouldUpdateFields() {
        // Arrange
        CategoryEntity entity = new CategoryEntity();
        String newName = "Limpeza";
        Long newId = 10L;

        // Act
        entity.setId(newId);
        entity.setCategoryName(newName);

        // Assert
        assertEquals(newId, entity.getId());
        assertEquals(newName, entity.getCategoryName());
    }

    @Test
    @DisplayName("Equals e HashCode: Devem ser consistentes com base nos valores dos campos")
    void equalsAndHashCode_ShouldBeConsistent() {
        // Arrange
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        Timestamp ts = new Timestamp(time);

        CategoryEntity entity1 = new CategoryEntity(1L, "Cat", date, ts);
        CategoryEntity entity2 = new CategoryEntity(1L, "Cat", date, ts);
        CategoryEntity entity3 = new CategoryEntity(2L, "Outra", date, ts);

        // Assert
        assertEquals(entity1, entity2, "Entidades com mesmos valores devem ser iguais");
        assertNotEquals(entity1, entity3, "Entidades com IDs diferentes devem ser desiguais");
        assertEquals(entity1.hashCode(), entity2.hashCode(), "HashCodes devem ser iguais para objetos iguais");
    }

    @Test
    @DisplayName("ToString: Deve conter as informações da entidade formatadas")
    void toString_ShouldContainEntityInfo() {
        // Arrange
        CategoryEntity entity = new CategoryEntity(1L, "Teste", null, null);

        // Act
        String result = entity.toString();

        // Assert
        assertTrue(result.contains("CategoryEntity"));
        assertTrue(result.contains("id=1"));
        assertTrue(result.contains("categoryName='Teste'"));
    }
}