package com.store.msshoppingcart.order.infrastructure.adapters.out.repository;

import com.store.msshoppingcart.order.infrastructure.adapters.in.dto.ProdutoCategoriaDTO;
import com.store.msshoppingcart.order.infrastructure.adapters.out.model.JPAProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

@Repository
public interface ProductCategoryGetNameRepository extends JpaRepository<JPAProdutoEntity, Long> {

    @Query("""
        SELECT new com.store.msshoppingcart.order.infrastructure.adapters.in.dto.ProdutoCategoriaDTO(
            p.nomeProduto,
            c.categoryName
        )
        FROM JPAProdutoEntity p
        JOIN p.nomeCategoria c
        WHERE p.id = :produtoId
    """)
    ProdutoCategoriaDTO findNameById(@Param("produtoId") Long produtoId);
}
