package gestaoeventos.api.rest;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
	
	@Value("${server.servlet.context-path:/}")
    private String contextPath;
	
    @Bean
    public OpenAPI customOpenAPI() {
    	
    	Server server = new Server();
        server.setUrl(contextPath);
        server.setDescription("Servidor da API de Gestão de Eventos");
    	
        return new OpenAPI()
        		.servers(List.of(server))
                .info(new Info()
                        .title("API - Gestão de Eventos")
                        .version("1.0")
                        .description("API REST para gerenciar eventos - CRUD completo com validações")
                        .contact(new Contact()
                                .name("Maurício Bulhões")
                                .email("mauricio_bulhoes@hotmail.com")));
        
    }
    
}