package dev.brunoaguiar.paycore.account.adapter.in.web;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class OpenApiConfiguration {

    @Bean
    OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
                .title("PayCore: Account Service")
                .description("Abertura e consulta da conta de crédito dos cartões")
                .version("0.1.0"));
    }
}
