package com.store.msshoppingcart.order;

import com.store.msshoppingcart.order.infrastructure.adapters.out.model.JPACategoryEntity;
import com.store.msshoppingcart.order.infrastructure.adapters.out.model.JPAProdutoEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JPAProdutoEntityTest {

    @Test
    @DisplayName("Deve inicializar todos os campos via construtor com argumentos")
    void constructor_ShouldSetAllFields() {
        // Arrange
        Long id = 1L;
        String nome = "Hambúrguer";
        JPACategoryEntity categoria = new JPACategoryEntity(10L, "Lanches");

        // Act
        JPAProdutoEntity entity = new JPAProdutoEntity(id, nome, categoria);

        // Assert
        assertAll("Validando integridade da entidade JPA Produto",
                () -> assertEquals(id, entity.getId()),
                () -> assertEquals(nome, entity.getNomeProduto()),
                () -> assertEquals(categoria, entity.getNomeCategoria())
        );
    }

    @Test
    @DisplayName("Setters: Deve permitir atualizar os dados do produto e sua categoria")
    void setters_ShouldUpdateFields() {
        // Arrange
        JPAProdutoEntity entity = new JPAProdutoEntity();
        Long newId = 50L;
        String newNome = "Refrigerante";
        JPACategoryEntity newCat = new JPACategoryEntity(20L, "Bebidas");

        // Act
        entity.setId(newId);
        entity.setNomeProduto(newNome);
        entity.setNomeCategoria(newCat);

        // Assert
        assertEquals(newId, entity.getId());
        assertEquals(newNome, entity.getNomeProduto());
        assertEquals(newCat, entity.getNomeCategoria());
    }

    @Test
    @DisplayName("Equals e HashCode: Devem considerar a igualdade de todos os campos")
    void equalsAndHashCode_ShouldBeConsistent() {
        // Arrange
        JPACategoryEntity cat = new JPACategoryEntity(1L, "Cat");
        JPAProdutoEntity p1 = new JPAProdutoEntity(10L, "Prod", cat);
        JPAProdutoEntity p2 = new JPAProdutoEntity(10L, "Prod", cat);
        JPAProdutoEntity p3 = new JPAProdutoEntity(11L, "Outro", cat);

        // Assert
        assertAll("Verificando contrato de igualdade",
                () -> assertEquals(p1, p2, "Produtos com mesmos dados devem ser iguais"),
                () -> assertEquals(p1.hashCode(), p2.hashCode(), "HashCodes devem ser idênticos"),
                () -> assertNotEquals(p1, p3, "Produtos com IDs diferentes devem ser desiguais"),
                () -> assertNotEquals(p1, null)
        );
    }

    @Test
    @DisplayName("Deve lidar com categoria nula sem lançar exceções")
    void handleNullCategory() {
        // Act
        JPAProdutoEntity entity = new JPAProdutoEntity(1L, "Sem Categoria", null);

        // Assert
        assertNull(entity.getNomeCategoria());
        assertNotNull(entity.getNomeProduto());
    }
}
