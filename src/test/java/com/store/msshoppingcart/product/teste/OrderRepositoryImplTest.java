package com.store.msshoppingcart.product.teste;

import com.store.msshoppingcart.order.domain.model.Order;
import com.store.msshoppingcart.order.infrastructure.adapters.in.dto.OrderResponseDTO;
import com.store.msshoppingcart.order.infrastructure.adapters.out.model.JPAOrderEntity;
import com.store.msshoppingcart.order.infrastructure.adapters.out.mappers.OrderMapper;
import com.store.msshoppingcart.order.infrastructure.adapters.out.repository.OrderAdaptersGetRepository;
import com.store.msshoppingcart.order.infrastructure.adapters.out.repository.impl.OrderRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderRepositoryImplTest {

    @Mock
    private OrderAdaptersGetRepository jpaRepository;

    @Mock
    private OrderMapper mapper;

    @InjectMocks
    private OrderRepositoryImpl orderRepository;

    @Test
    @DisplayName("save: Deve mapear o domínio para entidade e persistir no banco")
    void save_ShouldMapAndPersist() {
        // Arrange
        Order domainOrder = new Order();
        JPAOrderEntity entity = new JPAOrderEntity();
        when(mapper.toJPAOrderEntity(domainOrder)).thenReturn(entity);

        // Act
        orderRepository.save(domainOrder);

        // Assert
        verify(mapper, times(1)).toJPAOrderEntity(domainOrder);
        verify(jpaRepository, times(1)).save(entity);
    }

    @Test
    @DisplayName("findOrderById: Deve buscar no banco e retornar o DTO mapeado")
    void findOrderById_ShouldReturnMappedDto() {
        // Arrange
        String orderId = "ORD-123";
        JPAOrderEntity entity = new JPAOrderEntity();
        OrderResponseDTO responseDTO = new OrderResponseDTO();

        when(jpaRepository.findById(orderId)).thenReturn(Optional.of(entity));
        when(mapper.toOrderGetResponseDTO(Optional.of(entity))).thenReturn(Optional.of(responseDTO));

        // Act
        Optional<OrderResponseDTO> result = orderRepository.findOrderById(orderId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(responseDTO, result.get());
        verify(jpaRepository).findById(orderId);
    }

    @Test
    @DisplayName("findAll: Deve retornar uma página de DTOs mapeada")
    void findAll_ShouldReturnPageOfDtos() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<JPAOrderEntity> entityPage = new PageImpl<>(Collections.emptyList());
        Page<OrderResponseDTO> dtoPage = new PageImpl<>(Collections.emptyList());

        when(jpaRepository.findAll(pageable)).thenReturn(entityPage);
        when(mapper.toOrderGetResponseDTO(entityPage)).thenReturn(dtoPage);

        // Act
        Page<OrderResponseDTO> result = orderRepository.findAll(pageable);

        // Assert
        assertEquals(dtoPage, result);
        verify(jpaRepository).findAll(pageable);
    }

    @Test
    @DisplayName("orderId: Deve chamar a função de geração de ID do banco de dados")
    void orderId_ShouldCallDatabaseGenerator() {
        // Arrange
        String expectedId = "SEQ-001";
        when(jpaRepository.getByOrderId()).thenReturn(expectedId);

        // Act
        String result = orderRepository.orderId();

        // Assert
        assertEquals(expectedId, result);
        verify(jpaRepository).getByOrderId();
    }
}