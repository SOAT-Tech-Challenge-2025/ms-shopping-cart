package com.store.msshoppingcart.order.infrastructure.adapters.in.dto;

import java.util.List;
import java.util.Objects;

public class OrderSNSDTO {

    private String order_id;
    private Double total_order_value;
    private List<ProductSNS> products;

    // Construtor vazio
    public OrderSNSDTO() {
    }

    // Construtor completo
    public OrderSNSDTO(String orderId, Double totalOrderValue, List<ProductSNS> products) {
        this.order_id = orderId;
        this.total_order_value = totalOrderValue;
        this.products = products;
    }

    // Getters
    public String getOrder_id() {
        return order_id;
    }

    public Double getTotal_order_value() {
        return total_order_value;
    }

    public List<ProductSNS> getProducts() {
        return products;
    }

    // Setters
    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public void setTotal_order_value(Double total_order_value) {
        this.total_order_value = total_order_value;
    }

    public void setProducts(List<ProductSNS> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "OrderSNSDTO{" +
                "order_id='" + order_id + '\'' +
                ", total_order_value=" + total_order_value +
                ", products=" + products +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderSNSDTO that = (OrderSNSDTO) o;
        return Objects.equals(order_id, that.order_id) &&
                Objects.equals(total_order_value, that.total_order_value) &&
                Objects.equals(products, that.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order_id, total_order_value, products);
    }
}
