package com.store.msshoppingcart.order.infrastructure.adapters.in.dto;

public class ProductSNS {

    private String name;
    private Integer quantity;
    private Double unit_price;
    private String category;

    // Construtor vazio
    public ProductSNS() {
    }

    // Construtor completo
    public ProductSNS(String name,
                      Integer quantity,
                      Double unit_price,
                      String category) {
        this.name = name;
        this.quantity = quantity;
        this.unit_price = unit_price;
        this.category = category;
    }

    // Getters
    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getUnit_price() {
        return unit_price;
    }

    public String getCategory() {
        return category;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setUnit_price(Double unit_price) {
        this.unit_price = unit_price;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
