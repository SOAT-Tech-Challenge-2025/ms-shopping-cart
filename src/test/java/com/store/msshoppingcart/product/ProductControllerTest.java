package com.store.msshoppingcart.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.msshoppingcart.product.application.service.ProductServiceImpl;
import com.store.msshoppingcart.product.infrastructure.adapters.in.controller.ProductController;
import com.store.msshoppingcart.product.infrastructure.adapters.in.dto.ProductGetResponseDTO;
import com.store.msshoppingcart.product.infrastructure.adapters.in.dto.ProductPostUpResponseDTO;
import com.store.msshoppingcart.product.infrastructure.adapters.in.dto.ProductRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductServiceImpl productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /product - Deve criar um produto e retornar 201 Created")
    void createProduct_ShouldReturnCreated() throws Exception {
        // Arrange
        ProductRequestDTO request = new ProductRequestDTO();
        request.setProductName("Café");
        ProductPostUpResponseDTO response = new ProductPostUpResponseDTO("Produto salvo com sucesso");

        when(productService.saveProduct(any(ProductRequestDTO.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Produto salvo com sucesso"));
    }

    @Test
    @DisplayName("GET /product - Deve retornar página de produtos com paginação")
    void getCategories_ShouldReturnPage() throws Exception {
        // Arrange
        Page<ProductGetResponseDTO> page = new PageImpl<>(Collections.emptyList());
        when(productService.getAllProducts(any(Pageable.class))).thenReturn(page);

        // Act & Assert
        mockMvc.perform(get("/product")
                        .param("limit", "5")
                        .param("offset", "0")
                        .param("page", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    @DisplayName("GET /product/{id} - Deve retornar um produto por ID")
    void getProduct_ShouldReturnProduct() throws Exception {
        // Arrange
        Long id = 1L;
        ProductGetResponseDTO dto = new ProductGetResponseDTO();
        when(productService.getProductById(id)).thenReturn(Optional.of(dto));

        // Act & Assert
        mockMvc.perform(get("/product/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PUT /product/{id} - Deve atualizar o produto e retornar 200 OK")
    void updateProduct_ShouldReturnOk() throws Exception {
        // Arrange
        Long id = 1L;
        ProductRequestDTO request = new ProductRequestDTO();
        ProductPostUpResponseDTO response = new ProductPostUpResponseDTO("Produto atualizado");

        when(productService.updateProduct(eq(id), any(ProductRequestDTO.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(put("/product/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Produto atualizado"));
    }

    @Test
    @DisplayName("DELETE /product/{id} - Deve deletar o produto")
    void deleteProduct_ShouldReturnOk() throws Exception {
        // Arrange
        Long id = 1L;
        ProductPostUpResponseDTO response = new ProductPostUpResponseDTO("Produto excluído");
        when(productService.deleteProduct(id)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(delete("/product/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Produto excluído"));
    }
}
