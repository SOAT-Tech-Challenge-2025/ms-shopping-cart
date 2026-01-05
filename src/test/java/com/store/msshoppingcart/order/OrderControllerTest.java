package com.store.msshoppingcart.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.msshoppingcart.order.application.service.OrderServiceImpl;
import com.store.msshoppingcart.order.infrastructure.adapters.in.controller.OrderController;
import com.store.msshoppingcart.order.infrastructure.adapters.in.dto.OrderRequestDTO;
import com.store.msshoppingcart.order.infrastructure.adapters.in.dto.OrderResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderServiceImpl orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /order - Deve criar um pedido e retornar 201 Created")
    void createProduct_ShouldReturnCreated() throws Exception {
        // Arrange
        OrderRequestDTO request = new OrderRequestDTO();
        request.setClientId("CLI-1");
        // Não é necessário preencher produtos para testar apenas o Controller (Web Layer)

        // Act & Assert
        mockMvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(orderService, times(1)).saveOrder(any(OrderRequestDTO.class));
    }

    @Test
    @DisplayName("GET /order - Deve retornar página de pedidos com status 200")
    void getCategories_ShouldReturnPage() throws Exception {
        // Arrange
        Page<OrderResponseDTO> page = new PageImpl<>(Collections.emptyList());
        when(orderService.getAllOrders(any(Pageable.class))).thenReturn(page);

        // Act & Assert
        mockMvc.perform(get("/order")
                        .param("limit", "10")
                        .param("offset", "0")
                        .param("page", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());

        verify(orderService).getAllOrders(any(Pageable.class));
    }

    @Test
    @DisplayName("GET /order/{id} - Deve retornar pedido por ID")
    void getProduct_ShouldReturnOrder() throws Exception {
        // Arrange
        String id = "ORD-123";
        OrderResponseDTO response = new OrderResponseDTO();
        response.setOrderId(id);
        when(orderService.getOrdeById(id)).thenReturn(Optional.of(response));

        // Act & Assert
        mockMvc.perform(get("/order/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(id));
    }

    @Test
    @DisplayName("PUT /order/{id} - Deve atualizar o pedido e retornar 200")
    void updateProduct_ShouldReturnOk() throws Exception {
        // Arrange
        String id = "ORD-123";
        OrderRequestDTO request = new OrderRequestDTO();
        OrderResponseDTO response = new OrderResponseDTO();
        response.setOrderId(id);

        when(orderService.updateOrder(eq(id), any(OrderRequestDTO.class))).thenReturn(Optional.of(response));

        // Act & Assert
        mockMvc.perform(put("/order/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(id));
    }
}