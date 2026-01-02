package com.store.msshoppingcart.order.infrastructure.adapters.out.repository.impl;

import com.store.msshoppingcart.order.domain.model.Order;
import com.store.msshoppingcart.order.domain.repository.OrderRepository;
import com.store.msshoppingcart.order.infrastructure.adapters.in.dto.OrderResponseDTO;
import com.store.msshoppingcart.order.infrastructure.adapters.out.mappers.OrderMapper;
import com.store.msshoppingcart.order.infrastructure.adapters.out.repository.OrderAdaptersGetRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
 public class OrderRepositoryImpl implements OrderRepository {

    private final OrderAdaptersGetRepository repository;
    public final OrderMapper mapper;

    public OrderRepositoryImpl(OrderAdaptersGetRepository repository, OrderMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    @Override
    public void save(Order order) {
        repository.save(mapper.toJPAOrderEntity(order));
    }

    @Override
    public Page<OrderResponseDTO> findAll(Pageable pageable) {
        return mapper.toOrderGetResponseDTO(repository.findAll(pageable));
    }


    @Override
    public Optional<OrderResponseDTO> findOrderById(String orderId) {
        return mapper.toOrderGetResponseDTO(repository.findById(orderId));
    }

    @Override
    public String orderId() {
        return repository.getByOrderId();
    }

}
