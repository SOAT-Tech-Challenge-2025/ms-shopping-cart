package com.store.msshoppingcart.order.infrastructure.adapters.out.model;

import com.store.msshoppingcart.category.infrastructure.adapters.out.model.CategoryEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_categoria_itens")
public class JPACategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nm_categoria")
    private String categoryName;

    public JPACategoryEntity() {
    }

    public JPACategoryEntity(Long id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }


    public static CategoryEntity builder() {
        return new CategoryEntity();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + categoryName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CategoryEntity{" +
                "id=" + id +
                ", categoryName='" + categoryName +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JPACategoryEntity that = (JPACategoryEntity) o;
        return java.util.Objects.equals(id, that.id) &&
                java.util.Objects.equals(categoryName, that.categoryName);
    }



}