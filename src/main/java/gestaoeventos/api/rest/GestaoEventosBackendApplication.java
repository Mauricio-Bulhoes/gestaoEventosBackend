package gestaoeventos.api.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages = {"gestaoeventos.api.rest.model"})
@ComponentScan(basePackages = {"gestaoeventos.*"})
@EnableJpaRepositories(basePackages = {"gestaoeventos.api.rest.repository"})
@EnableTransactionManagement
@EnableCaching
public class GestaoEventosBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestaoEventosBackendApplication.class, args);
	}

}
