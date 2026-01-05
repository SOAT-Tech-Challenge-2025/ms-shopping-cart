package com.store.msshoppingcart.category.testes;

import com.store.msshoppingcart.category.infrastructure.adapters.in.dto.CategoryRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryRequestDTOTest {

    @Test
    @DisplayName("Deve inicializar o DTO corretamente via construtor com argumentos")
    void constructor_ShouldSetName() {
        // Arrange & Act
        String expectedName = "Higiene";
        CategoryRequestDTO dto = new CategoryRequestDTO(expectedName);

        // Assert
        assertEquals(expectedName, dto.getCategoryName());
    }

    @Test
    @DisplayName("Deve permitir alterar o nome via setter")
    void setter_ShouldUpdateName() {
        // Arrange
        CategoryRequestDTO dto = new CategoryRequestDTO();
        String newName = "Laticínios";

        // Act
        dto.setCategoryName(newName);

        // Assert
        assertEquals(newName, dto.getCategoryName());
    }

    @Test
    @DisplayName("Equals: Deve retornar true para DTOs com o mesmo nome")
    void equals_ShouldCompareCorrectly() {
        // Arrange
        CategoryRequestDTO dto1 = new CategoryRequestDTO("Papelaria");
        CategoryRequestDTO dto2 = new CategoryRequestDTO("Papelaria");
        CategoryRequestDTO dto3 = new CategoryRequestDTO("Limpeza");

        // Assert
        assertEquals(dto1, dto2, "DTOs com nomes iguais devem ser considerados iguais");
        assertNotEquals(dto1, dto3, "DTOs com nomes diferentes não devem ser iguais");
        assertNotEquals(dto1, null);
    }

    @Test
    @DisplayName("HashCode: Deve gerar o mesmo código para nomes idênticos")
    void hashCode_ShouldBeConsistent() {
        // Arrange
        CategoryRequestDTO dto1 = new CategoryRequestDTO("Pets");
        CategoryRequestDTO dto2 = new CategoryRequestDTO("Pets");

        // Assert
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    @DisplayName("ToString: Deve conter o nome da categoria na string")
    void toString_ShouldContainName() {
        // Arrange
        CategoryRequestDTO dto = new CategoryRequestDTO("Ferramentas");

        // Act
        String result = dto.toString();

        // Assert
        assertTrue(result.contains("categoryName='Ferramentas'"));
    }
}
