package com.store.msshoppingcart.product;

import com.store.msshoppingcart.product.application.service.ProductServiceImpl;
import com.store.msshoppingcart.product.domain.model.Product;
import com.store.msshoppingcart.product.infrastructure.adapters.in.dto.ProductGetResponseDTO;
import com.store.msshoppingcart.product.infrastructure.adapters.in.dto.ProductPostUpResponseDTO;
import com.store.msshoppingcart.product.infrastructure.adapters.in.dto.ProductRequestDTO;
import com.store.msshoppingcart.product.infrastructure.adapters.out.repository.impl.ProductRepositoryImpl;
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

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepositoryImpl adaptersRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    @DisplayName("saveProduct: Deve salvar produto com nome em maiúsculo")
    void saveProduct_ShouldConvertNameToUpperCaseAndSave() {
        // Arrange
        ProductRequestDTO request = new ProductRequestDTO();
        request.setProductName("hambúrguer");
        request.setIdCategory(1L);
        request.setUnitPrice(new BigDecimal("25.00"));
        request.setPreparationTime(15L);

        // Act
        ProductPostUpResponseDTO response = productService.saveProduct(request);

        // Assert
        assertEquals("Produto salvo com sucesso", response.getMessage());
        // Verifica se o domínio foi criado com o nome em UPPERCASE
        verify(adaptersRepository, times(1)).save(argThat(product ->
                product.getProductName().equals("HAMBÚRGUER")
        ));
    }

    @Test
    @DisplayName("getAllProducts: Deve retornar página de produtos do repositório")
    void getAllProducts_ShouldReturnPage() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductGetResponseDTO> expectedPage = new PageImpl<>(Collections.emptyList());
        when(adaptersRepository.findAll(pageable)).thenReturn(expectedPage);

        // Act
        Page<ProductGetResponseDTO> result = productService.getAllProducts(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(expectedPage, result);
        verify(adaptersRepository).findAll(pageable);
    }

    @Test
    @DisplayName("getProductById: Deve retornar produto quando ID existe")
    void getProductById_ShouldReturnProduct() {
        // Arrange
        Long id = 1L;
        ProductGetResponseDTO dto = new ProductGetResponseDTO();
        when(adaptersRepository.findById(id)).thenReturn(Optional.of(dto));

        // Act
        Optional<ProductGetResponseDTO> result = productService.getProductById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(dto, result.get());
    }

    @Test
    @DisplayName("updateProduct: Deve chamar o repositório para atualizar")
    void updateProduct_ShouldCallUpdate() {
        // Arrange
        Long id = 1L;
        ProductRequestDTO request = new ProductRequestDTO("Pizza", 2L, new BigDecimal("40.0"), 20L);
        ProductPostUpResponseDTO expectedResponse = new ProductPostUpResponseDTO("Atualizado");

        when(adaptersRepository.update(any(Product.class), eq(id))).thenReturn(expectedResponse);

        // Act
        ProductPostUpResponseDTO result = productService.updateProduct(id, request);

        // Assert
        assertEquals(expectedResponse, result);
        verify(adaptersRepository).update(any(Product.class), eq(id));
    }

    @Test
    @DisplayName("deleteProduct: Deve chamar o repositório para deletar")
    void deleteProduct_ShouldCallDelete() {
        // Arrange
        Long id = 1L;
        ProductPostUpResponseDTO expectedResponse = new ProductPostUpResponseDTO("Excluído");
        when(adaptersRepository.deleteById(id)).thenReturn(expectedResponse);

        // Act
        ProductPostUpResponseDTO result = productService.deleteProduct(id);

        // Assert
        assertEquals(expectedResponse, result);
        verify(adaptersRepository).deleteById(id);
    }
}
