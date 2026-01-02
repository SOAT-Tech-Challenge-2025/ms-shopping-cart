package com.store.msshoppingcart.order.domain.repository;

import com.store.msshoppingcart.order.infrastructure.adapters.in.dto.ProdutoCategoriaDTO;

public interface ProductCategoryRepository {
    ProdutoCategoriaDTO findCategoryById(Long categoryId);
}
