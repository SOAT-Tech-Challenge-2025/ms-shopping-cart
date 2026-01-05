package com.store.msshoppingcart.product;

import com.store.msshoppingcart.product.domain.model.Product;
import com.store.msshoppingcart.product.infrastructure.adapters.in.dto.ProductGetResponseDTO;
import com.store.msshoppingcart.product.infrastructure.adapters.in.dto.ProductPostUpResponseDTO;
import com.store.msshoppingcart.product.infrastructure.adapters.out.mappers.ProductMapper;
import com.store.msshoppingcart.product.infrastructure.adapters.out.model.ProductEntity;
import com.store.msshoppingcart.product.infrastructure.adapters.out.repository.ProductAdaptersGetRepository;
import com.store.msshoppingcart.product.infrastructure.adapters.out.repository.impl.ProductRepositoryImpl;
import com.store.msshoppingcart.utils.exception.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryImplTest {

    @Mock
    private ProductAdaptersGetRepository jpaRepository;

    @Mock
    private ProductMapper mapper;

    @InjectMocks
    private ProductRepositoryImpl productRepository;

    @Test
    @DisplayName("findById: Deve retornar DTO quando o produto existe")
    void findById_Success() {
        // Arrange
        Long id = 1L;
        ProductEntity entity = new ProductEntity();
        ProductGetResponseDTO expectedDto = new ProductGetResponseDTO();

        when(jpaRepository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.modelToProductGetResponseDTO(Optional.of(entity))).thenReturn(Optional.of(expectedDto));

        // Act
        Optional<ProductGetResponseDTO> result = productRepository.findById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedDto, result.get());
    }

    @Test
    @DisplayName("findById: Deve lançar CustomException 404 quando o produto não existe")
    void findById_NotFound() {
        // Arrange
        Long id = 99L;
        when(jpaRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> productRepository.findById(id));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Produto não encontrado", exception.getMessage());
    }

    @Test
    @DisplayName("save: Deve lançar 422 se o produto com mesmo nome já existir")
    void save_AlreadyExists() {
        // Arrange
        Product domain = new Product("Hambúrguer", 1L, BigDecimal.TEN, 10L);
        when(jpaRepository.findByProductName("Hambúrguer")).thenReturn(new ProductEntity());

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> productRepository.save(domain));
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, exception.getStatusCode());
    }

    @Test
    @DisplayName("save: Deve persistir o produto quando o nome é único")
    void save_Success() {
        // Arrange
        Product domain = new Product("Novo Produto", 1L, BigDecimal.TEN, 10L);
        ProductEntity entity = new ProductEntity();

        when(jpaRepository.findByProductName("Novo Produto")).thenReturn(null);
        when(mapper.productToProductEntityMap(domain)).thenReturn(entity);

        // Act
        productRepository.save(domain);

        // Assert
        verify(jpaRepository).save(entity);
    }

    @Test
    @DisplayName("findAll: Deve retornar página de DTOs mapeados")
    void findAll_Success() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 5);
        ProductEntity entity = new ProductEntity();
        Page<ProductEntity> entityPage = new PageImpl<>(List.of(entity));
        Page<ProductGetResponseDTO> dtoPage = new PageImpl<>(List.of(new ProductGetResponseDTO()));

        when(jpaRepository.findAll(pageable)).thenReturn(entityPage);
        when(mapper.modelToProductGetResponseDTO(entityPage)).thenReturn(dtoPage);

        // Act
        Page<ProductGetResponseDTO> result = productRepository.findAll(pageable);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.getTotalElements());
    }

    @Test
    @DisplayName("deleteById: Deve deletar produto existente")
    void deleteById_Success() {
        // Arrange
        Long id = 1L;
        when(jpaRepository.findById(id)).thenReturn(Optional.of(new ProductEntity()));

        // Act
        ProductPostUpResponseDTO response = productRepository.deleteById(id);

        // Assert
        assertEquals("Produto deletado com sucesso", response.getMessage());
        verify(jpaRepository).deleteById(id);
    }
}