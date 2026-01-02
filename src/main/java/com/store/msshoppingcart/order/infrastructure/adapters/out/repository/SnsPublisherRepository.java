package com.store.msshoppingcart.order.infrastructure.adapters.out.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface SnsPublisherRepository {
    void publish(String message);
}
