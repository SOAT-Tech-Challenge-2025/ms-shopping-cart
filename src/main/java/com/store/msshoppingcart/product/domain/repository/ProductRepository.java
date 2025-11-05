package com.store.msshoppingcart.product.domain.repository;

import com.store.msshoppingcart.product.domain.model.Product;
import com.store.msshoppingcart.product.infrastructure.adapters.in.dto.ProductGetResponseDTO;
import com.store.msshoppingcart.product.infrastructure.adapters.in.dto.ProductPostUpResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductRepository {

    Optional<ProductGetResponseDTO> findById(Long id);
    Page<ProductGetResponseDTO> findAll(Pageable pageable);
    void save(Product Product);
    ProductPostUpResponseDTO update(Product Product, Long id);
    ProductPostUpResponseDTO deleteById(Long id);

}
