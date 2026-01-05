package com.store.msshoppingcart.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.msshoppingcart.category.application.service.CategoryServiceImpl;
import com.store.msshoppingcart.category.infrastructure.adapters.in.controller.CategoryController;
import com.store.msshoppingcart.category.infrastructure.adapters.in.dto.CategoryRequestDTO;
import com.store.msshoppingcart.category.infrastructure.adapters.in.dto.CategoryResponseDTO;
import com.store.msshoppingcart.category.infrastructure.adapters.in.dto.CategoryWithProductsDTO;
import com.store.msshoppingcart.category.infrastructure.adapters.out.model.CategoryEntity;
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

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryServiceImpl categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /category - Deve criar uma categoria e retornar 201 Created")
    void createCategory_ShouldReturnCreated() throws Exception {
        // Arrange
        CategoryRequestDTO request = new CategoryRequestDTO("Eletrônicos");
        CategoryResponseDTO response = new CategoryResponseDTO("Categoria criada com sucesso");
        when(categoryService.saveCategory(any(CategoryRequestDTO.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Categoria criada com sucesso"));
    }

    @Test
    @DisplayName("GET /category - Deve retornar uma página de categorias com status 200")
    void getAllCategories_ShouldReturnPage() throws Exception {
        // Arrange
        Page<CategoryEntity> page = new PageImpl<>(Collections.emptyList());
        when(categoryService.getAllCategories(any(Pageable.class))).thenReturn(page);

        // Act & Assert
        mockMvc.perform(get("/category")
                        .param("limit", "5")
                        .param("page", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    @DisplayName("GET /category/{id} - Deve retornar categoria por ID")
    void getCategoryById_ShouldReturnCategory() throws Exception {
        // Arrange
        Long id = 1L;
        CategoryEntity entity = new CategoryEntity(); // Supondo que exista campos nela
        when(categoryService.getCategoryById(id)).thenReturn(Optional.of(entity));

        // Act & Assert
        mockMvc.perform(get("/category/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PUT /category/{id} - Deve atualizar e retornar 200")
    void updateCategory_ShouldReturnOk() throws Exception {
        // Arrange
        Long id = 1L;
        CategoryRequestDTO request = new CategoryRequestDTO("Novo Nome");
        CategoryResponseDTO response = new CategoryResponseDTO("Categoria atualizada");
        when(categoryService.updateCategory(eq(id), any(CategoryRequestDTO.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(put("/category/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Categoria atualizada"));
    }

    @Test
    @DisplayName("DELETE /category/{id} - Deve deletar e retornar 200")
    void deleteCategory_ShouldReturnOk() throws Exception {
        // Arrange
        Long id = 1L;
        CategoryResponseDTO response = new CategoryResponseDTO("Categoria deletada");
        when(categoryService.deleteCategory(id)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(delete("/category/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Categoria deletada"));
    }

    @Test
    @DisplayName("GET /category/{id}/products - Deve retornar DTO de produtos")
    void getProductsByCategoryId_ShouldReturnWithProducts() throws Exception {
        // Arrange
        Long id = 1L;
        CategoryWithProductsDTO dto = new CategoryWithProductsDTO();
        when(categoryService.getProductsByCategoryId(id)).thenReturn(Optional.of(dto));

        // Act & Assert
        mockMvc.perform(get("/category/{id}/products", id))
                .andExpect(status().isOk());
    }
}
