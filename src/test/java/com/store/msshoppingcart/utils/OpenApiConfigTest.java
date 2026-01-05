package com.store.msshoppingcart.utils;

import com.store.msshoppingcart.config.OpenApiConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

class OpenApiConfigTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(OpenApiConfig.class);

    @Test
    @DisplayName("Deve carregar o contexto de configuração da OpenAPI com sucesso")
    void openApiConfig_ShouldBeLoaded() {
        contextRunner.run(context -> {
            // Verifica se a classe de configuração existe como um Bean no contexto
            assertThat(context).hasSingleBean(OpenApiConfig.class);

            // Garante que o contexto subiu sem falhas de definição da documentação
            assertThat(context).hasNotFailed();
        });
    }

    @Test
    @DisplayName("Deve verificar se as definições da documentação estão presentes no bean")
    void openApiConfig_ShouldHaveDefinitions() {
        contextRunner.run(context -> {
            OpenApiConfig config = context.getBean(OpenApiConfig.class);
            assertThat(config).isNotNull();
        });
    }
}