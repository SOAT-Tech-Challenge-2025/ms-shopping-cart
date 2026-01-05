package com.store.msshoppingcart.product.teste;

import com.amazonaws.services.sns.AmazonSNS;
import com.store.msshoppingcart.order.config.LocalAwsConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

class LocalAwsConfigTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(LocalAwsConfig.class);

    @Test
    @DisplayName("Deve criar o bean AmazonSNS quando o perfil 'local' estiver ativo")
    void shouldCreateSnsBean_WhenLocalProfileIsActive() {
        contextRunner
                .withPropertyValues("spring.profiles.active=local")
                .run(context -> {
                    assertThat(context).hasSingleBean(AmazonSNS.class);
                    assertThat(context.getBean(AmazonSNS.class)).isNotNull();
                });
    }

    @Test
    @DisplayName("Não deve criar o bean AmazonSNS quando o perfil 'local' não estiver ativo")
    void shouldNotCreateSnsBean_WhenLocalProfileIsInactive() {
        contextRunner
                // Sem definir o profile 'local', a anotação @Profile("local") impede a criação
                .run(context -> {
                    assertThat(context).doesNotHaveBean(AmazonSNS.class);
                });
    }
}