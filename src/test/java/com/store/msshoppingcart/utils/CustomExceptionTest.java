package com.store.msshoppingcart.utils;

import com.store.msshoppingcart.utils.exception.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CustomExceptionTest {

    @Test
    @DisplayName("Deve armazenar corretamente todos os atributos passados no construtor")
    void constructor_ShouldSetAllFields() {
        // Arrange
        String message = "Erro de negócio";
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        String errorCode = "VAL-001";
        LocalDateTime now = LocalDateTime.now();
        UUID correlationId = UUID.randomUUID();

        // Act
        CustomException exception = new CustomException(message, status, errorCode, now, correlationId);

        // Assert
        assertAll("Verificando integridade da CustomException",
                () -> assertEquals(message, exception.getMessage()),
                () -> assertEquals(status, exception.getStatusCode()),
                () -> assertEquals(errorCode, exception.getErrorCode()),
                () -> assertEquals(now, exception.getTimestamp()),
                () -> assertEquals(correlationId, exception.getUuid())
        );
    }

    @Test
    @DisplayName("Deve permitir o lançamento da exceção e captura do tipo correto")
    void shouldBeThrowable() {
        // Act & Assert
        assertThrows(CustomException.class, () -> {
            throw new CustomException("Falha", HttpStatus.INTERNAL_SERVER_ERROR, "ERR", LocalDateTime.now(), UUID.randomUUID());
        });
    }
}