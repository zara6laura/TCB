package mx.gob.babien.tcb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    RestClient restClient(
            RestClient.Builder builder,
            @Value("${services.altacliente.base-url:http://localhost:8290}") String baseUrl
    ) {
        return builder.baseUrl(baseUrl).build();
    }
}
