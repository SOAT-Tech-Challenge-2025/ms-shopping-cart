package com.store.msshoppingcart.order;

import com.store.msshoppingcart.order.infrastructure.adapters.out.model.JPACategoryEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class JPACategoryEntityTest {

    @Test
    @DisplayName("Deve inicializar todos os campos via construtor com argumentos")
    void constructor_ShouldSetAllFields() {
        // Arrange
        Long id = 1L;
        String name = "Alimentos";

        // Act
        JPACategoryEntity entity = new JPACategoryEntity(id, name);

        // Assert
        assertAll("Validando integridade da entidade JPA",
                () -> assertEquals(id, entity.getId()),
                () -> assertEquals(name, entity.getCategoryName())
        );
    }

    @Test
    @DisplayName("Setters: Deve permitir atualizar os dados da entidade para persistência")
    void setters_ShouldUpdateFields() {
        // Arrange
        JPACategoryEntity entity = new JPACategoryEntity();
        Long newId = 10L;
        String newName = "Bebidas";

        // Act
        entity.setId(newId);
        entity.setCategoryName(newName);

        // Assert
        assertEquals(newId, entity.getId());
        assertEquals(newName, entity.getCategoryName());
    }

    @Test
    @DisplayName("Equals e HashCode: Devem ser consistentes com o estado da entidade")
    void equalsAndHashCode_ShouldBeConsistent() {
        // Arrange
        long time = System.currentTimeMillis();
        JPACategoryEntity entity1 = new JPACategoryEntity(1L, "Cat");
        JPACategoryEntity entity2 = new JPACategoryEntity(1L, "Cat");
        JPACategoryEntity entity3 = new JPACategoryEntity(2L, "Outra");

        // Assert
        assertEquals(entity1, entity2, "Entidades com mesmos valores devem ser iguais");
        assertNotEquals(entity1, entity3, "Entidades com IDs diferentes devem ser desiguais");
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("ToString: Deve conter as informações da categoria formatadas")
    void toString_ShouldContainEntityInfo() {
        // Arrange
        JPACategoryEntity entity = new JPACategoryEntity();
        entity.setId(5L);
        entity.setCategoryName("Eletrônicos");

        // Act
        String result = entity.toString();

        // Assert
        assertTrue(result.contains("id=5"));
        assertTrue(result.contains("categoryName='Eletrônicos"));
    }
}