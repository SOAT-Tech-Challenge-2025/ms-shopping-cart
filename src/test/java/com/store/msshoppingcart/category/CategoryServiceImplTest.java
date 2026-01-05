package com.store.msshoppingcart.category;

import com.store.msshoppingcart.category.application.service.CategoryServiceImpl;
import com.store.msshoppingcart.category.domain.model.Category;
import com.store.msshoppingcart.category.infrastructure.adapters.in.dto.CategoryRequestDTO;
import com.store.msshoppingcart.category.infrastructure.adapters.in.dto.CategoryResponseDTO;
import com.store.msshoppingcart.category.infrastructure.adapters.in.dto.CategoryWithProductsDTO;
import com.store.msshoppingcart.category.infrastructure.adapters.out.model.CategoryEntity;
import com.store.msshoppingcart.category.infrastructure.adapters.out.repository.impl.CategoryRepositoryImpl;
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

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepositoryImpl adaptersRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("Deve salvar uma categoria com sucesso e converter o nome para maiúsculo")
    void saveCategory_Success() {
        // GIVEN
        CategoryRequestDTO request = new CategoryRequestDTO("eletronicos");

        // WHEN
        CategoryResponseDTO response = categoryService.saveCategory(request);

        // THEN
        assertNotNull(response);
        assertEquals("Categoria criada com sucesso", response.getMessage());
        // Verifica se o repository.save foi chamado com o nome em UPPERCASE
        verify(adaptersRepository, times(1)).save(argThat(cat -> cat.getCategoryName().equals("ELETRONICOS")));
    }

    @Test
    @DisplayName("Deve retornar página de categorias")
    void getAllCategories_Success() {
        // GIVEN
        Pageable pageable = PageRequest.of(0, 10);
        Page<CategoryEntity> expectedPage = new PageImpl<>(Collections.emptyList());
        when(adaptersRepository.findAll(pageable)).thenReturn(expectedPage);

        // WHEN
        Page<CategoryEntity> result = categoryService.getAllCategories(pageable);

        // THEN
        assertEquals(expectedPage, result);
        verify(adaptersRepository).findAll(pageable);
    }

    @Test
    @DisplayName("Deve buscar categoria por ID")
    void getCategoryById_Success() {
        // GIVEN
        Long id = 1L;
        CategoryEntity entity = new CategoryEntity();
        when(adaptersRepository.findById(id)).thenReturn(Optional.of(entity));

        // WHEN
        Optional<CategoryEntity> result = categoryService.getCategoryById(id);

        // THEN
        assertTrue(result.isPresent());
        assertEquals(entity, result.get());
    }

    @Test
    @DisplayName("Deve atualizar categoria com sucesso")
    void updateCategory_Success() {
        // GIVEN
        Long id = 1L;
        CategoryRequestDTO request = new CategoryRequestDTO("Novo Nome");
        CategoryResponseDTO expectedResponse = new CategoryResponseDTO("Categoria atualizada");
        when(adaptersRepository.update(any(Category.class), eq(id))).thenReturn(expectedResponse);

        // WHEN
        CategoryResponseDTO result = categoryService.updateCategory(id, request);

        // THEN
        assertEquals(expectedResponse, result);
        verify(adaptersRepository).update(any(Category.class), eq(id));
    }

    @Test
    @DisplayName("Deve deletar categoria por ID")
    void deleteCategory_Success() {
        // GIVEN
        Long id = 1L;
        CategoryResponseDTO expectedResponse = new CategoryResponseDTO("Categoria deletada");
        when(adaptersRepository.deleteById(id)).thenReturn(expectedResponse);

        // WHEN
        CategoryResponseDTO result = categoryService.deleteCategory(id);

        // THEN
        assertEquals(expectedResponse, result);
        verify(adaptersRepository).deleteById(id);
    }

    @Test
    @DisplayName("Deve retornar produtos quando a categoria existe")
    void getProductsByCategoryId_Success() {
        // GIVEN
        Long id = 1L;
        CategoryWithProductsDTO expectedDto = new CategoryWithProductsDTO();
        when(adaptersRepository.findProductsByCategoryId(id)).thenReturn(Optional.of(expectedDto));

        // WHEN
        Optional<CategoryWithProductsDTO> result = categoryService.getProductsByCategoryId(id);

        // THEN
        assertTrue(result.isPresent());
        assertEquals(expectedDto, result.get());
    }

    @Test
    @DisplayName("Deve lançar CustomException quando a categoria não for encontrada ao buscar produtos")
    void getProductsByCategoryId_NotFound() {
        // GIVEN
        Long id = 1L;
        when(adaptersRepository.findProductsByCategoryId(id)).thenReturn(Optional.empty());

        // WHEN & THEN
        CustomException exception = assertThrows(CustomException.class, () -> {
            categoryService.getProductsByCategoryId(id);
        });

        assertEquals("Categoria não encontrada", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
}