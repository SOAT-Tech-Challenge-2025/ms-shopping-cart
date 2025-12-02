package com.store.msshoppingcart.order.application.usecases;

import com.store.msshoppingcart.order.infrastructure.adapters.in.dto.OrderRequestDTO;
import com.store.msshoppingcart.order.infrastructure.adapters.in.dto.OrderResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderUseCases {

    Optional<OrderResponseDTO> saveOrder(OrderRequestDTO Product);
    Page<OrderResponseDTO> getAllOrders(Pageable pageable);
    Optional<OrderResponseDTO> getOrdeById(String id);
    Optional<OrderResponseDTO> updateOrder(String id, OrderRequestDTO orderRequestDTO);
}
