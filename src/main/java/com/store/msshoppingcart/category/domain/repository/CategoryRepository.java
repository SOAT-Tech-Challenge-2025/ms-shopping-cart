package com.store.msshoppingcart.category.domain.repository;

import com.store.msshoppingcart.category.domain.model.Category;
import com.store.msshoppingcart.category.infrastructure.adapters.in.dto.CategoryResponseDTO;
import com.store.msshoppingcart.category.infrastructure.adapters.in.dto.CategoryWithProductsDTO;
import com.store.msshoppingcart.category.infrastructure.adapters.out.model.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CategoryRepository {

    Optional<CategoryEntity> findById(Long id);
    Page<CategoryEntity> findAll(Pageable pageable);
    void save(Category category);
    CategoryResponseDTO update(Category category, Long id);
    CategoryResponseDTO deleteById(Long id);
    Optional<CategoryWithProductsDTO> findProductsByCategoryId(Long id);

}
