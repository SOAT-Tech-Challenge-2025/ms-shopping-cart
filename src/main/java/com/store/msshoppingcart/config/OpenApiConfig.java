package com.store.msshoppingcart.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "ms-shopping-cart API",
                version = "v1"
        ),
        servers = {
                @Server(url = "/ms-shopping-cart")
        }
)
public class OpenApiConfig {
}
