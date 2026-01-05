package com.store.msshoppingcart.category.infrastructure.adapters.out.mappers.impl;

import com.store.msshoppingcart.category.domain.model.Category;
import com.store.msshoppingcart.category.infrastructure.adapters.in.dto.CategoryProductProjectionDTO;
import com.store.msshoppingcart.category.infrastructure.adapters.in.dto.CategoryWithProductsDTO;
import com.store.msshoppingcart.category.infrastructure.adapters.out.model.CategoryEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CategoryMapperImplIT {

    private CategoryMapperImpl categoryMapper;

    @BeforeEach
    void setUp() {
        categoryMapper = new CategoryMapperImpl();
    }

    @Test
    void shouldMapCategoryToCategoryEntity() {
        // Given
        Category category = new Category("Test Category");

        // When
        CategoryEntity result = categoryMapper.toCategoryEntityMap(category);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getCategoryName()).isEqualTo("Test Category");
        assertThat(result.getDateInclusion()).isNotNull();
        assertThat(result.getTimestamp()).isNotNull();
    }

    @Test
    void shouldMapCategoryToUpdateCategoryEntity() {
        // Given
        Category category = new Category("Updated Category");
        Long id = 1L;

        // When
        CategoryEntity result = categoryMapper.toCategoryUpdateMap(category, id);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getCategoryName()).isEqualTo("Updated Category");
        assertThat(result.getDateInclusion()).isNotNull();
        assertThat(result.getTimestamp()).isNotNull();
    }

    @Test
    void shouldMapProductProjectionsToCategoryWithProducts() {
        // Given
        List<CategoryProductProjectionDTO> projections = new ArrayList<>();
        CategoryProductProjectionDTO projection1 = mock(CategoryProductProjectionDTO.class);
        CategoryProductProjectionDTO projection2 = mock(CategoryProductProjectionDTO.class);

        // Configure first projection (category info)
        when(projection1.getCategoriaId()).thenReturn(1L);
        when(projection1.getNomeCategoria()).thenReturn("Test Category");
        when(projection1.getProdutoId()).thenReturn(1L);
        when(projection1.getNomeProduto()).thenReturn("Product 1");
        when(projection1.getVlUnitarioProduto()).thenReturn(new BigDecimal("10.00"));
        when(projection1.getTempoDePreparo()).thenReturn(15);
        when(projection1.getDtInclusao()).thenReturn(new Date(System.currentTimeMillis()));

        // Configure second projection (another product)
        when(projection2.getCategoriaId()).thenReturn(1L);
        when(projection2.getNomeCategoria()).thenReturn("Test Category");
        when(projection2.getProdutoId()).thenReturn(2L);
        when(projection2.getNomeProduto()).thenReturn("Product 2");
        when(projection2.getVlUnitarioProduto()).thenReturn(new BigDecimal("20.00"));
        when(projection2.getTempoDePreparo()).thenReturn(30);
        when(projection2.getDtInclusao()).thenReturn(new Date(System.currentTimeMillis()));

        projections.add(projection1);
        projections.add(projection2);

        // When
        Optional<CategoryWithProductsDTO> result = categoryMapper.toProductCategoryEntity(projections);

        // Then
        assertThat(result).isPresent();
        CategoryWithProductsDTO dto = result.get();
        assertThat(dto.getCategoriaId()).isEqualTo(1L);
        assertThat(dto.getNomeCategoria()).isEqualTo("Test Category");
        assertThat(dto.getProdutos()).hasSize(2);
        assertThat(dto.getProdutos().get(0).getProductId()).isEqualTo(1L);
        assertThat(dto.getProdutos().get(1).getProductId()).isEqualTo(2L);
        assertThat(dto.getProdutos().get(0).getNameProduct()).isEqualTo("Product 1");
        assertThat(dto.getProdutos().get(1).getNameProduct()).isEqualTo("Product 2");
        assertThat(dto.getProdutos().get(0).getUnitPrice()).isEqualTo(new BigDecimal("10.00"));
        assertThat(dto.getProdutos().get(1).getUnitPrice()).isEqualTo(new BigDecimal("20.00"));
    }

    @Test
    void shouldReturnEmptyWhenProjectionListIsNull() {
        // When
        Optional<CategoryWithProductsDTO> result = categoryMapper.toProductCategoryEntity(null);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnEmptyWhenProjectionListIsEmpty() {
        // When
        Optional<CategoryWithProductsDTO> result = categoryMapper.toProductCategoryEntity(new ArrayList<>());

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void shouldFilterOutNullProductIds() {
        // Given
        List<CategoryProductProjectionDTO> projections = new ArrayList<>();
        CategoryProductProjectionDTO projection1 = mock(CategoryProductProjectionDTO.class);
        CategoryProductProjectionDTO projection2 = mock(CategoryProductProjectionDTO.class);

        // Configure first projection with valid product
        when(projection1.getCategoriaId()).thenReturn(1L);
        when(projection1.getNomeCategoria()).thenReturn("Test Category");
        when(projection1.getProdutoId()).thenReturn(1L);
        when(projection1.getNomeProduto()).thenReturn("Product 1");
        when(projection1.getVlUnitarioProduto()).thenReturn(new BigDecimal("10.00"));
        when(projection1.getTempoDePreparo()).thenReturn(15);
        when(projection1.getDtInclusao()).thenReturn(new Date(System.currentTimeMillis()));

        // Configure second projection with null product id
        when(projection2.getCategoriaId()).thenReturn(1L);
        when(projection2.getNomeCategoria()).thenReturn("Test Category");
        when(projection2.getProdutoId()).thenReturn(null);

        projections.add(projection1);
        projections.add(projection2);

        // When
        Optional<CategoryWithProductsDTO> result = categoryMapper.toProductCategoryEntity(projections);

        // Then
        assertThat(result).isPresent();
        CategoryWithProductsDTO dto = result.get();
        assertThat(dto.getProdutos()).hasSize(1);
        assertThat(dto.getProdutos().get(0).getProductId()).isEqualTo(1L);
    }
}
