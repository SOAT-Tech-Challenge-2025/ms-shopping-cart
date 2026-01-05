package com.store.msshoppingcart.order;

import com.store.msshoppingcart.order.domain.model.Order;
import com.store.msshoppingcart.order.domain.model.OrderProduct;
import com.store.msshoppingcart.order.infrastructure.adapters.in.dto.OrderResponseDTO;
import com.store.msshoppingcart.order.infrastructure.adapters.out.mappers.impl.OrderMapperImpl;
import com.store.msshoppingcart.order.infrastructure.adapters.out.model.JPAOrderEntity;
import com.store.msshoppingcart.order.infrastructure.adapters.out.model.JPAOrderProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class OrderMapperImplTest {

    private OrderMapperImpl orderMapper;

    @BeforeEach
    void setUp() {
        orderMapper = new OrderMapperImpl();
    }

    @Test
    @DisplayName("toJPAOrderEntity: Deve mapear domínio Order para entidade JPA com itens vinculados")
    void toJPAOrderEntity_ShouldMapCorrectly() {
        // Arrange
        OrderProduct product = new OrderProduct("ORD-1", 10L, 2, 20.0);
        Order order = new Order("ORD-1", 20.0, 15, "CLI-1", List.of(product));

        // Act
        JPAOrderEntity result = orderMapper.toJPAOrderEntity(order);

        // Assert
        assertThat(result.getId()).isEqualTo(order.getId());
        assertThat(result.getTotalAmountOrder()).isEqualTo(order.getTotalAmountOrder());
        assertThat(result.getProdutos()).hasSize(1);

        JPAOrderProductEntity itemResult = result.getProdutos().get(0);
        assertThat(itemResult.getQtItem()).isEqualTo(product.getQtItem());
        assertThat(itemResult.getOrder()).isEqualTo(result); // Valida back-reference para JPA
        assertThat(itemResult.getId().getProductId()).isEqualTo(10L);
    }

    @Test
    @DisplayName("toOrderGetResponseDTO (Optional): Deve converter entidade para DTO de resposta")
    void toOrderGetResponseDTO_Optional_ShouldMapCorrectly() {
        // Arrange
        JPAOrderEntity entity = new JPAOrderEntity();
        entity.setId("ORD-1");
        entity.setClientId("CLI-1");
        entity.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));

        JPAOrderProductEntity prodEntity = new JPAOrderProductEntity();
        prodEntity.setProductId(10L);
        prodEntity.setQtItem(5);
        prodEntity.setVlQtItem(50.0);
        entity.setProdutos(List.of(prodEntity));

        // Act
        Optional<OrderResponseDTO> result = orderMapper.toOrderGetResponseDTO(Optional.of(entity));

        // Assert
        assertThat(result).isPresent();
        OrderResponseDTO dto = result.get();
        assertThat(dto.getOrderId()).isEqualTo(entity.getId());
        assertThat(dto.getClientId()).isEqualTo(entity.getClientId());
        assertThat(dto.getProducts()).hasSize(1);
        assertThat(dto.getProducts().get(0).getProductId()).isEqualTo(10L);
    }

    @Test
    @DisplayName("toOrderGetResponseDTO (Optional): Deve retornar empty quando entrada for vazia")
    void toOrderGetResponseDTO_Optional_ShouldReturnEmpty() {
        assertThat(orderMapper.toOrderGetResponseDTO(Optional.empty())).isEmpty();
    }

    @Test
    @DisplayName("toOrderGetResponseDTO (Page): Deve mapear página de entidades para página de DTOs")
    void toOrderGetResponseDTO_Page_ShouldMapCorrectly() {
        // Arrange
        JPAOrderEntity entity = new JPAOrderEntity();
        entity.setId("ORD-PAGE");
        Page<JPAOrderEntity> entityPage = new PageImpl<>(List.of(entity));

        // Act
        Page<OrderResponseDTO> resultPage = orderMapper.toOrderGetResponseDTO(entityPage);

        // Assert
        assertThat(resultPage.getContent()).hasSize(1);
        assertThat(resultPage.getContent().get(0).getOrderId()).isEqualTo("ORD-PAGE");
    }
}