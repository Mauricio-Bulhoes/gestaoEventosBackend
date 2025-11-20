package gestaoeventos.api.rest.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import gestaoeventos.api.rest.model.Evento;

// Esta anotação configura um ambiente de teste Spring Boot para testes de JPA
// e configura um banco de dados em memória (H2 por padrão)
@DataJpaTest 
@ActiveProfiles("test") // Opção: Para usar um application-test.properties específico
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) // Força uso do H2 em memória
public class EventoRepositoryIntegrationTest {

    @Autowired
    private EventoRepository eventoRepository;

    @Test
    public void deveSalvarUmEventoComSucesso() {
        // ARRANGE
        Evento evento = new Evento();
        evento.setTitulo("Teste de Integração");
        evento.setDescricao("Descrição do evento de teste de integração");
        evento.setLocal("Local do Teste");
        // A data deve ser futura para ser considerada válida
        evento.setDataHora(LocalDateTime.now().plusDays(5)); 
        
        // ACT
        // O método save() real do JPA é chamado e persiste no banco de dados em memória
        Evento eventoSalvo = eventoRepository.save(evento);

        // ASSERT
        // Verifica se o evento não é nulo após o salvamento
        assertNotNull(eventoSalvo);
        // Verifica se um ID foi gerado
        assertNotNull(eventoSalvo.getId());
        // Verifica se o título foi persistido corretamente
        assertEquals("Teste de Integração", eventoSalvo.getTitulo());

        // O @DataJpaTest reverterá esta transação (rollback) automaticamente
        // mantendo o banco de dados limpo.
    }
}