package com.store.msshoppingcart.category;

import com.store.msshoppingcart.category.infrastructure.adapters.in.dto.CategoryWithProductsDTO;
import com.store.msshoppingcart.category.infrastructure.adapters.in.dto.ProductDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryWithProductsDTOTest {

    @Test
    @DisplayName("Deve inicializar o DTO com lista de produtos via construtor")
    void constructor_ShouldInitializeAllFields() {
        // Arrange
        Long categoriaId = 1L;
        String nomeCategoria = "Eletrônicos";
        List<ProductDTO> produtos = new ArrayList<>();
        produtos.add(new ProductDTO()); // Supondo que ProductDTO exista

        // Act
        CategoryWithProductsDTO dto = new CategoryWithProductsDTO(categoriaId, nomeCategoria, produtos);

        // Assert
        assertEquals(categoriaId, dto.getCategoriaId());
        assertEquals(nomeCategoria, dto.getNomeCategoria());
        assertEquals(1, dto.getProdutos().size());
        assertEquals(produtos, dto.getProdutos());
    }

    @Test
    @DisplayName("Deve permitir alterar os campos via setters")
    void setters_ShouldUpdateFields() {
        // Arrange
        CategoryWithProductsDTO dto = new CategoryWithProductsDTO();
        Long newId = 10L;
        String newName = "Limpeza";
        List<ProductDTO> newProducts = new ArrayList<>();

        // Act
        dto.setCategoriaId(newId);
        dto.setNomeCategoria(newName);
        dto.setProdutos(newProducts);

        // Assert
        assertEquals(newId, dto.getCategoriaId());
        assertEquals(newName, dto.getNomeCategoria());
        assertEquals(newProducts, dto.getProdutos());
    }

    @Test
    @DisplayName("Deve lidar com lista de produtos nula ou vazia")
    void handleEmptyProducts() {
        // Act
        CategoryWithProductsDTO dto = new CategoryWithProductsDTO();
        dto.setProdutos(null);

        // Assert
        assertNull(dto.getProdutos());

        dto.setProdutos(new ArrayList<>());
        assertTrue(dto.getProdutos().isEmpty());
    }
}