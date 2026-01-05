package com.store.msshoppingcart.order;

import com.store.msshoppingcart.order.application.service.OrderServiceImpl;
import com.store.msshoppingcart.order.domain.repository.ProductCategoryRepository;
import com.store.msshoppingcart.order.infrastructure.adapters.in.dto.*;
import com.store.msshoppingcart.order.infrastructure.adapters.out.repository.impl.OrderRepositoryImpl;
import com.store.msshoppingcart.order.infrastructure.adapters.out.repository.impl.SnsPublisherRepositoryImpl;
import com.store.msshoppingcart.utils.exception.CustomException;
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
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepositoryImpl adaptersRepository;

    @Mock
    private ProductCategoryRepository adapterProductCategoryRepository;

    @Mock
    private SnsPublisherRepositoryImpl snsPublisherRepositoryImpl;

    @InjectMocks
    private OrderServiceImpl orderService;

    // ... existing code ...
    // ... existing code ...
// ... existing code ...

    // ... existing code ...
    @Test
    @DisplayName("saveOrder: Deve lançar CustomException quando o pedido não é encontrado após salvar")
    void saveOrder_ErrorFlow() {
        // Arrange
        String orderId = "ORD-999";
        OrderRequestDTO request = new OrderRequestDTO();
        request.setProducts(Collections.emptyList());

        when(adaptersRepository.orderId()).thenReturn(orderId);
        // Simulamos que o pedido não foi encontrado após o save
        when(adaptersRepository.findOrderById(orderId)).thenReturn(Optional.empty());

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () ->
                orderService.saveOrder(request)
        );

        // O catch genérico do service relança como "Erro ao gerar o pedido"
        assertEquals("Erro ao gerar o pedido", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());

        verify(adaptersRepository).save(any());
        verify(adaptersRepository).findOrderById(orderId);
    }
// ... existing code ...

    @Test
    @DisplayName("getAllOrders: Deve retornar página de pedidos via repository")
    void getAllOrders_Success() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<OrderResponseDTO> page = new PageImpl<>(Collections.emptyList());
        when(adaptersRepository.findAll(pageable)).thenReturn(page);

        // Act
        Page<OrderResponseDTO> result = orderService.getAllOrders(pageable);

        // Assert
        assertNotNull(result);
        verify(adaptersRepository).findAll(pageable);
    }

    @Test
    @DisplayName("updateOrder: Deve lançar 404 quando o pedido para atualização não existe")
    void updateOrder_NotFound() {
        // Arrange
        String id = "NON_EXISTENT";
        when(adaptersRepository.findOrderById(id)).thenReturn(Optional.empty());

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () ->
                orderService.updateOrder(id, new OrderRequestDTO())
        );
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
}