package com.store.msshoppingcart.product;

import com.store.msshoppingcart.product.domain.model.Product;
import com.store.msshoppingcart.product.infrastructure.adapters.in.dto.ProductGetResponseDTO;
import com.store.msshoppingcart.product.infrastructure.adapters.out.mappers.impl.ProductMapperImpl;
import com.store.msshoppingcart.product.infrastructure.adapters.out.model.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ProductMapperImplTest {

    private ProductMapperImpl mapper;

    @BeforeEach
    void setUp() {
        mapper = new ProductMapperImpl();
    }

    @Test
    @DisplayName("productToProductEntityMap: Deve mapear domínio para entidade JPA")
    void productToProductEntityMap_ShouldMapAllFields() {
        // Arrange
        Product domain = new Product("Pizza", 1L, new BigDecimal("45.0"), 20L);

        // Act
        ProductEntity entity = mapper.productToProductEntityMap(domain);

        // Assert
        assertThat(entity.getProductName()).isEqualTo("Pizza");
        assertThat(entity.getUnitPrice()).isEqualTo(new BigDecimal("45.0"));
        assertThat(entity.getPreparationTime()).isEqualTo(20);
        assertThat(entity.getInclusionDate()).isEqualTo(domain.getDateInclusion());
    }

    @Test
    @DisplayName("productToProductUpdateMap: Deve mapear domínio mantendo o ID fornecido")
    void productToProductUpdateMap_ShouldIncludeId() {
        // Arrange
        Long id = 500L;
        Product domain = new Product("Sushi", 2L, new BigDecimal("60.0"), 30L);

        // Act
        ProductEntity entity = mapper.productToProductUpdateMap(domain, id);

        // Assert
        assertThat(entity.getId()).isEqualTo(id);
        assertThat(entity.getProductName()).isEqualTo("Sushi");
    }

    @Test
    @DisplayName("modelToProductGetResponseDTO (Optional): Deve converter entidade para DTO")
    void modelToProductGetResponseDTO_Optional_ShouldMapCorrectly() {
        // Arrange
        ProductEntity entity = new ProductEntity();
        entity.setId(1L);
        entity.setProductName("Hambúrguer");
        entity.setPreparationTime(15);
        entity.setUnitPrice(new BigDecimal("25.0"));

        // Act
        Optional<ProductGetResponseDTO> result = mapper.modelToProductGetResponseDTO(Optional.of(entity));

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getNameProduct()).isEqualTo("Hambúrguer");
        assertThat(result.get().getPreparationTime()).isEqualTo(15L);
    }

    @Test
    @DisplayName("modelToProductGetResponseDTO (Page): Deve mapear e ordenar por ID")
    void modelToProductGetResponseDTO_Page_ShouldSortById() {
        // Arrange
        ProductEntity p1 = new ProductEntity(); p1.setId(2L); p1.setPreparationTime(10);
        ProductEntity p2 = new ProductEntity(); p2.setId(1L); p2.setPreparationTime(10);

        Page<ProductEntity> page = new PageImpl<>(List.of(p1, p2), PageRequest.of(0, 10), 2);

        // Act
        Page<ProductGetResponseDTO> result = mapper.modelToProductGetResponseDTO(page);

        // Assert
        assertThat(result.getContent()).hasSize(2);
        // Verifica se a ordenação por ID (Comparator no mapper) funcionou
        assertThat(result.getContent().get(0).getId()).isEqualTo(1L);
        assertThat(result.getContent().get(1).getId()).isEqualTo(2L);
    }
}