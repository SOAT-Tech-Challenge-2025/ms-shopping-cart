package com.store.msshoppingcart.product.teste;


import com.amazonaws.services.sns.AmazonSNS;
import com.store.msshoppingcart.order.config.AwsConfig;
import com.store.msshoppingcart.order.config.LocalAwsConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

class AwsConfigTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(AwsConfig.class);

    @Test
    @DisplayName("AwsConfig: Deve criar o bean snsClient com a região configurada")
    void snsClientBean_ShouldBeCreated_InCloudConfig() {
        contextRunner
                .withPropertyValues("aws.region=us-east-1")
                .run(context -> {
                    assertThat(context).hasSingleBean(AmazonSNS.class);
                    assertThat(context.getBean(AmazonSNS.class)).isNotNull();
                });
    }

    @Test
    @DisplayName("LocalAwsConfig: Deve criar bean para perfil local com endpoint específico")
    void snsClientBean_ShouldBeCreated_InLocalProfile() {
        new ApplicationContextRunner()
                .withUserConfiguration(LocalAwsConfig.class)
                .withPropertyValues("spring.profiles.active=local")
                .run(context -> {
                    assertThat(context).hasSingleBean(AmazonSNS.class);
                    // Como é configuração local, o bean deve existir mesmo sem a propriedade @Value da AwsConfig
                    assertThat(context.getBean(AmazonSNS.class)).isNotNull();
                });
    }
}