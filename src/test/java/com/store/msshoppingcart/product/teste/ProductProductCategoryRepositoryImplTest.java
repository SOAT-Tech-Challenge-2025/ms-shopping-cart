package com.store.msshoppingcart.product.teste;

import com.store.msshoppingcart.order.infrastructure.adapters.in.dto.ProdutoCategoriaDTO;
import com.store.msshoppingcart.order.infrastructure.adapters.out.repository.ProductCategoryGetNameRepository;
import com.store.msshoppingcart.order.infrastructure.adapters.out.repository.impl.ProductProductCategoryRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductProductCategoryRepositoryImplTest {

    @Mock
    private ProductCategoryGetNameRepository jpaRepository;

    @InjectMocks
    private ProductProductCategoryRepositoryImpl productCategoryRepository;

    @Test
    @DisplayName("findCategoryById: Deve retornar o DTO quando o produto existe")
    void findCategoryById_Success() {
        // Arrange
        Long produtoId = 1L;
        ProdutoCategoriaDTO expectedDto = new ProdutoCategoriaDTO("Hambúrguer", "Lanches");
        when(jpaRepository.findNameById(produtoId)).thenReturn(expectedDto);

        // Act
        ProdutoCategoriaDTO result = productCategoryRepository.findCategoryById(produtoId);

        // Assert
        assertEquals(expectedDto, result);
        assertEquals("Hambúrguer", result.getNomeProduto());
        assertEquals("Lanches", result.getNomeCategoria());
        verify(jpaRepository, times(1)).findNameById(produtoId);
    }

    @Test
    @DisplayName("findCategoryById: Deve retornar null quando o repositório não encontrar o produto")
    void findCategoryById_NotFound() {
        // Arrange
        Long produtoId = 99L;
        when(jpaRepository.findNameById(produtoId)).thenReturn(null);

        // Act
        ProdutoCategoriaDTO result = productCategoryRepository.findCategoryById(produtoId);

        // Assert
        assertNull(result);
        verify(jpaRepository).findNameById(produtoId);
    }
}