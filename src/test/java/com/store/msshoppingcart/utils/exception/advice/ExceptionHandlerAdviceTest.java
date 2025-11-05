package com.store.msshoppingcart.utils.exception.advice;

import com.store.msshoppingcart.utils.exception.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExceptionHandlerAdviceTest {

    private ExceptionHandlerAdvice exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new ExceptionHandlerAdvice();
    }

    @Test
    void handleCustomException_ShouldReturnCorrectResponseEntity() {
        // Arrange
        String message = "Test error message";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String errorCode = "ERR-001";
        LocalDateTime timestamp = LocalDateTime.now();
        UUID uuid = UUID.randomUUID();

        CustomException exception = new CustomException(message, status, errorCode, timestamp, uuid);

        // Act
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleCustomException(exception);

        // Assert
        assertEquals(status, response.getStatusCode());

        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(message, body.get("message"));
        assertEquals(status.value(), body.get("statusCode"));
        assertEquals(errorCode, body.get("errorCode"));
        assertEquals(timestamp.toString(), body.get("timestamp"));
        assertEquals(uuid.toString(), body.get("uuid"));
    }

    @Test
    void handleValidationExceptions_ShouldReturnCorrectResponseEntity() {
        // Arrange
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);

        FieldError fieldError = new FieldError("objectName", "fieldName", "Field validation error");
        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(List.of(fieldError));

        // Act
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleValidationExceptions(ex);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(HttpStatus.BAD_REQUEST.value(), body.get("statusCode"));
        assertNotNull(body.get("message"));
        assertEquals("ERROR_VALIDATION", body.get("errorCode"));
        assertNotNull(body.get("timestamp"));
        assertNotNull(body.get("uuid"));
    }
}
