package com.store.msshoppingcart.category;

import com.store.msshoppingcart.category.infrastructure.adapters.in.dto.CategoryProductProjectionDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class CategoryProductProjectionDTOTest {

    @Test
    @DisplayName("Deve criar o DTO corretamente através do construtor")
    void constructor_ShouldSetAllFields() {
        // Arrange
        Long categoriaId = 1L;
        String nomeCategoria = "Bebidas";
        Long produtoId = 10L;
        String nomeProduto = "Suco de Laranja";
        BigDecimal preco = new BigDecimal("12.50");
        Integer tempoPreparo = 15;
        Date dataInclusao = new Date(System.currentTimeMillis());

        // Act
        CategoryProductProjectionDTO dto = new CategoryProductProjectionDTO(
                categoriaId, nomeCategoria, produtoId, nomeProduto, preco, tempoPreparo, dataInclusao
        );

        // Assert
        assertAll("Validando campos do construtor",
                () -> assertEquals(categoriaId, dto.getCategoriaId()),
                () -> assertEquals(nomeCategoria, dto.getNomeCategoria()),
                () -> assertEquals(produtoId, dto.getProdutoId()),
                () -> assertEquals(nomeProduto, dto.getNomeProduto()),
                () -> assertEquals(preco, dto.getVlUnitarioProduto()),
                () -> assertEquals(tempoPreparo, dto.getTempoDePreparo()),
                () -> assertEquals(dataInclusao, dto.getDtInclusao())
        );
    }

    @Test
    @DisplayName("Deve permitir alteração de valores através dos setters")
    void setters_ShouldUpdateFields() {
        // Arrange
        CategoryProductProjectionDTO dto = new CategoryProductProjectionDTO(
                null, null, null, null, null, null, null
        );
        String novoNome = "Nova Categoria";
        BigDecimal novoPreco = new BigDecimal("20.00");

        // Act
        dto.setNomeCategoria(novoNome);
        dto.setVlUnitarioProduto(novoPreco);
        dto.setCategoriaId(5L);

        // Assert
        assertEquals(novoNome, dto.getNomeCategoria());
        assertEquals(novoPreco, dto.getVlUnitarioProduto());
        assertEquals(5L, dto.getCategoriaId());
    }

    @Test
    @DisplayName("Deve lidar com campos nulos (simulando LEFT JOIN sem correspondência)")
    void constructor_WithNulls_ShouldHandleCorrectly() {
        // Arrange & Act
        CategoryProductProjectionDTO dto = new CategoryProductProjectionDTO(
                1L, "Categoria Vazia", null, null, null, null, null
        );

        // Assert
        assertNotNull(dto.getCategoriaId());
        assertNull(dto.getProdutoId());
        assertNull(dto.getVlUnitarioProduto());
    }
}
