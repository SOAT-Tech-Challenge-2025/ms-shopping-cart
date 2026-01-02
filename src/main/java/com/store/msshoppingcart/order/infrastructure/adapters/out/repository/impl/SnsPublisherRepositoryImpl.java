package com.store.msshoppingcart.order.infrastructure.adapters.out.repository.impl;

import com.store.msshoppingcart.order.infrastructure.adapters.out.repository.SnsPublisherRepository;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class SnsPublisherRepositoryImpl implements SnsPublisherRepository {

    private final AmazonSNS snsClient;
    private final String topicArn;

    public SnsPublisherRepositoryImpl(
            AmazonSNS snsClient,
            @Value("${sns.service.topic.arn}") String topicArn
    ) {
        this.snsClient = snsClient;
        this.topicArn = topicArn;
    }

    @Override
    public void publish(String message) {
        PublishRequest request = new PublishRequest()
                .withTopicArn(topicArn)
                .withMessage(message);

        snsClient.publish(request);
    }
}

