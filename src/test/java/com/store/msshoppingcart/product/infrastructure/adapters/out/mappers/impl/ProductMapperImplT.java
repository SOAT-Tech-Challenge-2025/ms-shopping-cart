package com.store.msshoppingcart.product.infrastructure.adapters.out.mappers.impl;

import com.store.msshoppingcart.product.domain.model.Product;
import com.store.msshoppingcart.product.infrastructure.adapters.in.dto.ProductGetResponseDTO;
import com.store.msshoppingcart.product.infrastructure.adapters.out.model.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperImplT {

    private ProductMapperImpl productMapper;
    private ProductEntity productEntity;
    private Product product;

    @BeforeEach
    void setUp() {
        productMapper = new ProductMapperImpl();

        productEntity = new ProductEntity();
        productEntity.setId(1L);
        productEntity.setProductName("Test Product");
        productEntity.setIdCategory(1L);
        productEntity.setUnitPrice(BigDecimal.valueOf(10.99));
        productEntity.setPreparationTime(15);

        product = new Product();
        product.setProductName("Test Product");
        product.setIdCategory(1L);
        product.setUnitPrice(BigDecimal.valueOf(10.99));
        product.setPreparationTime(15L);
    }

    @Test
    void modelToProductGetResponseDTO_Page_ShouldMapCorrectly() {
        // Arrange
        List<ProductEntity> productList = List.of(productEntity);
        Page<ProductEntity> productPage = new PageImpl<>(productList, PageRequest.of(0, 10), 1);

        // Act
        Page<ProductGetResponseDTO> result = productMapper.modelToProductGetResponseDTO(productPage);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        ProductGetResponseDTO dto = result.getContent().get(0);
        assertEquals(productEntity.getId(), dto.getId());
        assertEquals(productEntity.getProductName(), dto.getNameProduct());
        assertEquals(productEntity.getIdCategory(), dto.getIdCategory());
        assertEquals(productEntity.getUnitPrice(), dto.getUnitPrice());
        assertEquals(productEntity.getPreparationTime().longValue(), dto.getPreparationTime());
    }

    @Test
    void modelToProductGetResponseDTO_Optional_ShouldMapCorrectly() {
        // Arrange
        Optional<ProductEntity> productOptional = Optional.of(productEntity);

        // Act
        Optional<ProductGetResponseDTO> result = productMapper.modelToProductGetResponseDTO(productOptional);

        // Assert
        assertTrue(result.isPresent());
        ProductGetResponseDTO dto = result.get();
        assertEquals(productEntity.getId(), dto.getId());
        assertEquals(productEntity.getProductName(), dto.getNameProduct());
        assertEquals(productEntity.getIdCategory(), dto.getIdCategory());
        assertEquals(productEntity.getUnitPrice(), dto.getUnitPrice());
        assertEquals(productEntity.getPreparationTime().longValue(), dto.getPreparationTime());
    }

    @Test
    void modelToProductGetResponseDTO_Optional_Empty_ShouldReturnEmpty() {
        // Arrange
        Optional<ProductEntity> productOptional = Optional.empty();

        // Act
        Optional<ProductGetResponseDTO> result = productMapper.modelToProductGetResponseDTO(productOptional);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void productToProductUpdateMap_ShouldMapCorrectly() {
        // Arrange
        Long id = 1L;

        // Act
        ProductEntity result = productMapper.productToProductUpdateMap(product, id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(product.getProductName(), result.getProductName());
        assertEquals(product.getIdCategory(), result.getIdCategory());
    }
}
