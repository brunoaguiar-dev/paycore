package dev.brunoaguiar.paycore.card.adapter.in.web;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class OpenApiConfiguration {

    @Bean
    OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
                .title("PayCore: Card Service")
                .description("Emissão, consulta, bloqueio e desbloqueio de cartões")
                .version("0.1.0"));
    }
}
