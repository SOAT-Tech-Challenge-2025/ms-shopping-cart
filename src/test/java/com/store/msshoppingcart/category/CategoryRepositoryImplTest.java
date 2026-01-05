package com.store.msshoppingcart.category;

import com.store.msshoppingcart.category.domain.model.Category;
import com.store.msshoppingcart.category.infrastructure.adapters.in.dto.CategoryResponseDTO;
import com.store.msshoppingcart.category.infrastructure.adapters.out.mappers.CategoryMapper;
import com.store.msshoppingcart.category.infrastructure.adapters.out.model.CategoryEntity;
import com.store.msshoppingcart.category.infrastructure.adapters.out.repository.CategoryAdaptersRepository;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryRepositoryImplTest {

    @Mock
    private CategoryAdaptersRepository jpaRepository;

    @Mock
    private CategoryMapper mapper;

    @InjectMocks
    private CategoryRepositoryImpl categoryRepository;

    @Test
    @DisplayName("findById: Deve retornar a entidade quando encontrada")
    void findById_Success() {
        // Arrange
        Long id = 1L;
        CategoryEntity entity = new CategoryEntity();
        when(jpaRepository.findById(id)).thenReturn(Optional.of(entity));

        // Act
        Optional<CategoryEntity> result = categoryRepository.findById(id);

        // Assert
        assertTrue(result.isPresent());
        verify(jpaRepository).findById(id);
    }

    @Test
    @DisplayName("findById: Deve lançar CustomException 404 quando não encontrar")
    void findById_NotFound() {
        // Arrange
        Long id = 99L;
        when(jpaRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> categoryRepository.findById(id));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Categoria não encontrada", exception.getMessage());
    }

    @Test
    @DisplayName("save: Deve lançar exceção 422 se a categoria já existir pelo nome")
    void save_AlreadyExists() {
        // Arrange
        Category domainCategory = new Category("Bebidas");
        when(jpaRepository.findByCategoryName("Bebidas")).thenReturn(new CategoryEntity());

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> categoryRepository.save(domainCategory));
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, exception.getStatusCode());
        assertEquals("Categoria já existe", exception.getMessage());
    }

    @Test
    @DisplayName("save: Deve persistir nova categoria com sucesso")
    void save_Success() {
        // Arrange
        Category domainCategory = new Category("Limpeza");
        CategoryEntity entity = new CategoryEntity();

        when(jpaRepository.findByCategoryName("Limpeza")).thenReturn(null);
        when(mapper.toCategoryEntityMap(domainCategory)).thenReturn(entity);

        // Act
        categoryRepository.save(domainCategory);

        // Assert
        verify(jpaRepository).save(entity);
    }

    @Test
    @DisplayName("deoleteById: Deve deletar produtos e categoria quando o ID existe")
    void deleteById_Success() {
        // Arrange
        Long id = 1L;
        CategoryEntity entity = new CategoryEntity();
        when(jpaRepository.findById(id)).thenReturn(Optional.of(entity));

        // Act
        CategoryResponseDTO response = categoryRepository.deoleteById(id);

        // Assert
        assertEquals("Categoria deletada", response.getMessage());
        verify(jpaRepository).deleteProductsByCategoryId(id);
        verify(jpaRepository).deleteCategoryById(id);
    }

    @Test
    @DisplayName("findAll: Deve retornar página se houver dados")
    void findAll_Success() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 5);
        CategoryEntity entity = new CategoryEntity();
        Page<CategoryEntity> page = new PageImpl<>(List.of(entity));
        when(jpaRepository.findAll(pageable)).thenReturn(page);

        // Act
        Page<CategoryEntity> result = categoryRepository.findAll(pageable);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.getTotalElements());
    }

    @Test
    @DisplayName("findProductsByCategoryId: Deve lidar com erro genérico e lançar CustomException")
    void findProducts_ErrorHandling() {
        // Arrange
        Long id = 1L;
        when(jpaRepository.findCategoryWithProducts(id)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> categoryRepository.findProductsByCategoryId(id));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Erro ao pesquisar produtos"));
    }
}