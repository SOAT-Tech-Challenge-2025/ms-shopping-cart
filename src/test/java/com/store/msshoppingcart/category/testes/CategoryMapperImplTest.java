package com.store.msshoppingcart.category.testes;

import com.store.msshoppingcart.category.domain.model.Category;
import com.store.msshoppingcart.category.infrastructure.adapters.in.dto.CategoryProductProjectionDTO;
import com.store.msshoppingcart.category.infrastructure.adapters.in.dto.CategoryWithProductsDTO;
import com.store.msshoppingcart.category.infrastructure.adapters.out.mappers.impl.CategoryMapperImpl;
import com.store.msshoppingcart.category.infrastructure.adapters.out.model.CategoryEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryMapperImplTest {

    private CategoryMapperImpl mapper;

    @BeforeEach
    void setUp() {
        mapper = new CategoryMapperImpl();
    }

    @Test
    @DisplayName("Deve mapear domínio Category para CategoryEntity")
    void toCategoryEntityMap_ShouldMapAllFields() {
        // Arrange
        Category category = new Category("Eletrônicos");

        // Act
        CategoryEntity entity = mapper.toCategoryEntityMap(category);

        // Assert
        assertThat(entity).isNotNull();
        assertThat(entity.getCategoryName()).isEqualTo("Eletrônicos");
        assertThat(entity.getDateInclusion()).isEqualTo(category.getDateInclusion());
        assertThat(entity.getTimestamp()).isEqualTo(category.getTimestamp());
    }

    @Test
    @DisplayName("Deve mapear atualização de categoria mantendo o ID fornecido")
    void toCategoryUpdateMap_ShouldIncludeId() {
        // Arrange
        Long id = 5L;
        Category category = new Category("Moda");

        // Act
        CategoryEntity entity = mapper.toCategoryUpdateMap(category, id);

        // Assert
        assertThat(entity.getId()).isEqualTo(id);
        assertThat(entity.getCategoryName()).isEqualTo("Moda");
    }

    @Test
    @DisplayName("Deve converter lista de projeções em DTO com produtos agrupados e ordenados")
    void toProductCategoryEntity_ShouldGroupAndSortProducts() {
        // Arrange
        Date now = new Date(System.currentTimeMillis());
        List<CategoryProductProjectionDTO> projections = new ArrayList<>();

        // Produto 2 (ID maior)
        projections.add(new CategoryProductProjectionDTO(1L, "Cat 1", 20L, "Prod B", new BigDecimal("10.0"), 5, now));
        // Produto 1 (ID menor)
        projections.add(new CategoryProductProjectionDTO(1L, "Cat 1", 10L, "Prod A", new BigDecimal("5.0"), 2, now));

        // Act
        Optional<CategoryWithProductsDTO> result = mapper.toProductCategoryEntity(projections);

        // Assert
        assertThat(result).isPresent();
        CategoryWithProductsDTO dto = result.get();
        assertThat(dto.getCategoriaId()).isEqualTo(1L);
        assertThat(dto.getNomeCategoria()).isEqualTo("Cat 1");
        assertThat(dto.getProdutos()).hasSize(2);

        // Verifica ordenação por ID (conforme o .sorted() no mapper)
        assertThat(dto.getProdutos().get(0).getProductId()).isEqualTo(10L);
        assertThat(dto.getProdutos().get(1).getProductId()).isEqualTo(20L);
    }

    @Test
    @DisplayName("Deve filtrar produtos nulos da lista de projeções (ex: LEFT JOIN sem resultados)")
    void toProductCategoryEntity_ShouldFilterNullProducts() {
        // Arrange
        List<CategoryProductProjectionDTO> projections = new ArrayList<>();
        // Projeção com produto nulo
        projections.add(new CategoryProductProjectionDTO(1L, "Vazia", null, null, null, null, null));

        // Act
        Optional<CategoryWithProductsDTO> result = mapper.toProductCategoryEntity(projections);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getProdutos()).isEmpty();
    }

    @Test
    @DisplayName("Deve retornar Optional vazio para lista de projeções nula ou vazia")
    void toProductCategoryEntity_ShouldReturnEmptyWhenInputIsInvalid() {
        assertThat(mapper.toProductCategoryEntity(null)).isEmpty();
        assertThat(mapper.toProductCategoryEntity(new ArrayList<>())).isEmpty();
    }
}