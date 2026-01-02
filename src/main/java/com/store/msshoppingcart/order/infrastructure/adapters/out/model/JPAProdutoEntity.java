package com.store.msshoppingcart.order.infrastructure.adapters.out.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tb_produto")
public class JPAProdutoEntity {

    @Id
    private Long id;

    @Column(name = "nm_produto")
    private String nomeProduto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria")
    private JPACategoryEntity nomeCategoria;

    public JPAProdutoEntity() {
    }

    public JPAProdutoEntity(Long id, String nomeProduto, JPACategoryEntity categoria) {
        this.id = id;
        this.nomeProduto = nomeProduto;
        this.nomeCategoria = categoria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public JPACategoryEntity getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(JPACategoryEntity categoria) {
        this.nomeCategoria = categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        JPAProdutoEntity that = (JPAProdutoEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(nomeProduto, that.nomeProduto) && Objects.equals(nomeCategoria, that.nomeCategoria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomeProduto, nomeCategoria);
    }
}