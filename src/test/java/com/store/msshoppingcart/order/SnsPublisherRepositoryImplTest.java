package com.store.msshoppingcart.order;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.store.msshoppingcart.order.infrastructure.adapters.out.repository.impl.SnsPublisherRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SnsPublisherRepositoryImplTest {

    @Mock
    private AmazonSNS snsClient;

    private final String topicArn = "arn:aws:sns:us-east-1:123456789012:order-topic.fifo";

    private SnsPublisherRepositoryImpl snsPublisherRepository;

    @BeforeEach
    void setUp() {
        // Inicializamos manualmente para injetar o valor do topicArn que viria do @Value
        snsPublisherRepository = new SnsPublisherRepositoryImpl(snsClient, topicArn);
    }

    @Test
    @DisplayName("publish: Deve construir o PublishRequest com os parâmetros corretos e chamar o cliente SNS")
    void publish_ShouldSendWithCorrectParameters() {
        // Arrange
        String messageBody = "{\"orderId\": \"ORD-123\", \"status\": \"CREATED\"}";
        ArgumentCaptor<PublishRequest> requestCaptor = ArgumentCaptor.forClass(PublishRequest.class);

        // Act
        snsPublisherRepository.publish(messageBody);

        // Assert
        verify(snsClient, times(1)).publish(requestCaptor.capture());

        PublishRequest capturedRequest = requestCaptor.getValue();

        assertAll("Validando campos da requisição SNS",
                () -> assertEquals(topicArn, capturedRequest.getTopicArn(), "O ARN do tópico deve ser o configurado"),
                () -> assertEquals(messageBody, capturedRequest.getMessage(), "O corpo da mensagem deve ser o enviado"),
                () -> assertEquals("order", capturedRequest.getMessageGroupId(), "O MessageGroupId deve ser 'order'"),
                () -> assertNotNull(capturedRequest.getMessageDeduplicationId(), "O DeduplicationId deve ser gerado (UUID)")
        );
    }

    @Test
    @DisplayName("publish: Deve lançar exceção se o cliente SNS falhar")
    void publish_ShouldPropagateException() {
        // Arrange
        String messageBody = "fail-message";
        // Simulamos um erro de runtime do SDK da AWS
        org.mockito.Mockito.doThrow(new RuntimeException("AWS Error")).when(snsClient).publish(org.mockito.ArgumentMatchers.any(PublishRequest.class));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> snsPublisherRepository.publish(messageBody));
    }
}