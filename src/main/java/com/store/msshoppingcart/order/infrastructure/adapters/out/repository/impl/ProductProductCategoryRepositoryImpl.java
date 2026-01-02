package com.store.msshoppingcart.order.infrastructure.adapters.out.repository.impl;



import com.store.msshoppingcart.order.domain.repository.ProductCategoryRepository;
import com.store.msshoppingcart.order.infrastructure.adapters.in.dto.ProdutoCategoriaDTO;
import com.store.msshoppingcart.order.infrastructure.adapters.out.repository.ProductCategoryGetNameRepository;
import org.springframework.stereotype.Component;

@Component
 public class ProductProductCategoryRepositoryImpl implements ProductCategoryRepository {
    private final ProductCategoryGetNameRepository repository;

    public ProductProductCategoryRepositoryImpl(ProductCategoryGetNameRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProdutoCategoriaDTO findCategoryById(Long categoryId) {
        return repository.findNameById(categoryId);
    }
}
