package com.store.msshoppingcart.order.infrastructure.adapters.in.dto;

public class ProdutoCategoriaDTO {
    private final String nomeProduto;
    private final String nomeCategoria;

    public ProdutoCategoriaDTO(String nomeProduto, String nomeCategoria) {
        this.nomeProduto = nomeProduto;
        this.nomeCategoria = nomeCategoria;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }
}
