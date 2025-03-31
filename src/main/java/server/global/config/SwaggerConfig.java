package server.global.config;

import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addServersItem(new Server().url("https://nowskhu.duckdns")) // 도메인주소 입력할 것
                .info(new Info().title("Nowskhu API")
                        .description("API documentation for nowskhu")
                        .version("v1.0.0"));
    }
}
