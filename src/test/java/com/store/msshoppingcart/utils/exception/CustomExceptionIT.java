package com.store.msshoppingcart.utils.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CustomExceptionIT {

    @Test
    void customException_ShouldCreateWithCorrectAttributes() {
        // Arrange
        String message = "Test error message";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String errorCode = "ERR-001";
        LocalDateTime timestamp = LocalDateTime.now();
        UUID uuid = UUID.randomUUID();

        // Act
        CustomException exception = new CustomException(message, status, errorCode, timestamp, uuid);

        // Assert
        assertEquals(message, exception.getMessage());
        assertEquals(status, exception.getStatusCode());
        assertEquals(errorCode, exception.getErrorCode());
        assertEquals(timestamp, exception.getTimestamp());
        assertEquals(uuid, exception.getUuid());
    }
}
