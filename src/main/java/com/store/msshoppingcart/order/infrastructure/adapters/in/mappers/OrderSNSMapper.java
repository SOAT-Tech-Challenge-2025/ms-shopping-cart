package com.store.msshoppingcart.order.infrastructure.adapters.in.mappers;

import com.store.msshoppingcart.order.infrastructure.adapters.in.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderSNSMapper {

    OrderSNSMapper INSTANCE = Mappers.getMapper(OrderSNSMapper.class);

    @Mapping(source = "orderId", target = "order_id")
    @Mapping(source = "totalOrder", target = "total_order_value")
    OrderSNSDTO toSNS(OrderResponseDTO source);

    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "vlUnitProduct", target = "unit_price")
    ProductSNS toSNS(ProductResponse source);
}
