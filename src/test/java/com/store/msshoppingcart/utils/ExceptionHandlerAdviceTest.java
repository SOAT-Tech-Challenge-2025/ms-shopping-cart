package com.store.msshoppingcart.utils;

import com.store.msshoppingcart.utils.exception.CustomException;
import com.store.msshoppingcart.utils.exception.advice.ExceptionHandlerAdvice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExceptionHandlerAdviceTest {

    private ExceptionHandlerAdvice advice;

    @BeforeEach
    void setUp() {
        advice = new ExceptionHandlerAdvice();
    }

    @Test
    @DisplayName("Deve formatar corretamente a resposta para CustomException")
    void handleCustomException_ShouldReturnFormattedResponse() {
        // Arrange
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = "Recurso não encontrado";
        String errorCode = "ERR-404";
        LocalDateTime now = LocalDateTime.now();
        UUID uuid = UUID.randomUUID();

        CustomException ex = new CustomException(message, status, errorCode, now, uuid);

        // Act
        ResponseEntity<Map<String, Object>> response = advice.handleCustomException(ex);

        // Assert
        assertEquals(status, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(status.value(), body.get("statusCode"));
        assertEquals(message, body.get("message"));
        assertEquals(errorCode, body.get("errorCode"));
        assertEquals(now.toString(), body.get("timestamp"));
        assertEquals(uuid.toString(), body.get("uuid"));
    }

    @Test
    @DisplayName("Deve extrair mensagens de validação para MethodArgumentNotValidException")
    @SuppressWarnings("unchecked")
    void handleValidationExceptions_ShouldReturnListofErrors() {
        // Arrange
        MethodParameter parameter = mock(MethodParameter.class);
        BindingResult bindingResult = mock(BindingResult.class);

        // Simulando dois erros de validação
        ObjectError error1 = new ObjectError("obj", "O nome é obrigatório");
        ObjectError error2 = new ObjectError("obj", "O preço deve ser positivo");

        when(bindingResult.getAllErrors()).thenReturn(List.of(error1, error2));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(parameter, bindingResult);

        // Act
        ResponseEntity<Map<String, Object>> response = advice.handleValidationExceptions(ex);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);

        List<String> messages = (List<String>) body.get("message");
        assertEquals(2, messages.size());
        assertTrue(messages.contains("O nome é obrigatório"));
        assertTrue(messages.contains("O preço deve ser positivo"));
        assertEquals("ERROR_VALIDATION", body.get("errorCode"));
    }
}
