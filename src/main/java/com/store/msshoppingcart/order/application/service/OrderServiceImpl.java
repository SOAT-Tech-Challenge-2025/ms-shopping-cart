package com.store.msshoppingcart.order.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.msshoppingcart.order.application.usecases.OrderUseCases;
import com.store.msshoppingcart.order.domain.model.Order;
import com.store.msshoppingcart.order.domain.model.OrderProduct;
import com.store.msshoppingcart.order.infrastructure.adapters.in.dto.*;
import com.store.msshoppingcart.order.infrastructure.adapters.in.mappers.OrderSNSMapper;
import com.store.msshoppingcart.order.infrastructure.adapters.in.utils.OrderUtils;
import com.store.msshoppingcart.order.infrastructure.adapters.out.repository.impl.ProductProductCategoryRepositoryImpl;
import com.store.msshoppingcart.order.infrastructure.adapters.out.repository.impl.SnsPublisherRepositoryImpl;
import com.store.msshoppingcart.order.infrastructure.adapters.out.repository.impl.OrderRepositoryImpl;
import com.store.msshoppingcart.utils.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class OrderServiceImpl implements OrderUseCases {

    public final OrderRepositoryImpl adaptersRepository;
    public final ProductProductCategoryRepositoryImpl adapterProductCategoryRepository;
    private final SnsPublisherRepositoryImpl snsPublisherRepositoryImpl;

    public OrderServiceImpl(OrderRepositoryImpl adaptersRepository, ProductProductCategoryRepositoryImpl adapterProductCategoryRepository, SnsPublisherRepositoryImpl snsPublisherRepositoryImpl) {
        this.adaptersRepository = adaptersRepository;
        this.adapterProductCategoryRepository = adapterProductCategoryRepository;
        this.snsPublisherRepositoryImpl = snsPublisherRepositoryImpl;
    }

    @Override
    public void saveOrder(OrderRequestDTO orderRequestDTO) {

        OrderSNSMapper orderSNSMapper = OrderSNSMapper.INSTANCE;
        String orderId = adaptersRepository.orderId();

        List<OrderProduct> orderProducts =
                OrderUtils.groupAndSumProducts(orderRequestDTO.getProducts(), orderId);

        double totalOrder = orderProducts.stream()
                .mapToDouble(OrderProduct::getVlQtItem)
                .sum();

        Order orderRequestModelModel = new Order(
                orderId,
                totalOrder,
                OrderUtils.somarPreparationTime(orderRequestDTO.getProducts()),
                orderRequestDTO.getClientId(),
                orderProducts
        );

        try {
            adaptersRepository.save(orderRequestModelModel);
            Optional<OrderResponseDTO> orderResponseOpt =
                    adaptersRepository.findOrderById(orderId);

            if (orderResponseOpt.isEmpty()) {
                throw new CustomException(
                        "Pedido não encontrado para publicação no SNS",
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                        LocalDateTime.now(),
                        UUID.randomUUID()
                );
            }

            OrderResponseDTO orderResponse = orderResponseOpt.get();

            OrderSNSDTO orderSNS = orderSNSMapper.toSNS(orderResponse);
            List<ProductSNS> enrichedProducts = orderResponse.getProducts()
                    .stream()
                    .map(productResponse -> {

                        ProdutoCategoriaDTO produtoCategoria =
                                adapterProductCategoryRepository
                                        .findCategoryById(productResponse.getProductId());

                        ProductSNS productSNS = new ProductSNS();
                        productSNS.setName(produtoCategoria.getNomeProduto());
                        productSNS.setCategory(produtoCategoria.getNomeCategoria());
                        productSNS.setQuantity(productResponse.getQuantity());
                        productSNS.setUnit_price(productResponse.getVlUnitProduct());

                        return productSNS;
                    })
                    .toList();

            orderSNS.setProducts(enrichedProducts);

            // Publica no SNS
            try {
                String message = new ObjectMapper().writeValueAsString(orderSNS);
                snsPublisherRepositoryImpl.publish(message);
            }catch (Exception e) {
                    log.error("Erro ao publicar mensagem no SNS. OrderId={}", orderId, e);

                    throw new CustomException(
                            "Erro ao publicar mensagem no SNS",
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                            LocalDateTime.now(),
                            UUID.randomUUID()
                    );
                }


            } catch (Exception e) {
            throw new CustomException(
                    "Erro ao gerar o pedido",
                    HttpStatus.BAD_REQUEST,
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
    }


    @Override
    public Page<OrderResponseDTO> getAllOrders(Pageable pageable) {
        try {
            return adaptersRepository.findAll(pageable);
        }catch (Exception e) {
            throw new CustomException(
                    "Erro ao buscar os pedidos",
                    HttpStatus.BAD_REQUEST,
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
    }

    @Override
    public Optional<OrderResponseDTO> getOrdeById(String id) {
        return adaptersRepository.findOrderById(id);
    }

    @Override
    public Optional<OrderResponseDTO> updateOrder(String id, OrderRequestDTO orderRequestDTO){
        OrderResponseDTO orderResponseDTO = getOrdeById(id).orElseThrow(() -> new CustomException(
                "Pedido não encontrado",
                HttpStatus.NOT_FOUND,
                String.valueOf(HttpStatus.NOT_FOUND.value()),
                LocalDateTime.now(),
                UUID.randomUUID()
        ));

        List<OrderProduct> orderProducts = OrderUtils.groupAndSumProducts(orderRequestDTO.getProducts(), id);
        double totalOrder = orderProducts.stream()
                .mapToDouble(OrderProduct::getVlQtItem)
                .sum();
        Order orderRequestModelModel = new Order(id, totalOrder, OrderUtils.somarPreparationTime(orderRequestDTO.getProducts()), orderRequestDTO.getClientId(), orderProducts);

        try {
            adaptersRepository.save(orderRequestModelModel);
        }catch (Exception e) {
            throw new CustomException(
                    "Erro ao atualizar o pedido",
                    HttpStatus.BAD_REQUEST,
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
        return adaptersRepository.findOrderById(id);
    }

}

