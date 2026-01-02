package com.store.msshoppingcart.order.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local")
public class LocalAwsConfig {

    private static final String SNS_ENDPOINT = "http://localhost:4566";
    private static final String REGION = "us-east-1";

    @Bean
    public AmazonSNS snsClient() {
        return AmazonSNSClientBuilder
                .standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(SNS_ENDPOINT, REGION)
                )
                .withCredentials(
                        new AWSStaticCredentialsProvider(
                                new BasicAWSCredentials("test", "test")
                        )
                )
                .build();
    }
}