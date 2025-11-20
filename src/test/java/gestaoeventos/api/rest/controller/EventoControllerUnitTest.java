package gestaoeventos.api.rest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import gestaoeventos.api.rest.model.Evento;
import gestaoeventos.api.rest.repository.EventoRepository;

@ExtendWith(MockitoExtension.class)
public class EventoControllerUnitTest {

    @InjectMocks
    private EventoController eventoController;

    @Mock
    private EventoRepository eventoRepository;

    private Evento eventoMock;

    @BeforeEach
    public void setup() {
        // Inicializa o controller com o mock do repositório
        eventoController = new EventoController(eventoRepository);

        // Cria um objeto Evento de mock para ser usado nos testes
        eventoMock = new Evento();
        eventoMock.setId(1L);
        eventoMock.setTitulo("Test Evento");
        eventoMock.setLocal("Test Local");
        // A data deve ser futura para passar na validação (embora o mock não execute a validação real)
        eventoMock.setDataHora(LocalDateTime.now().plusDays(1)); 
    }

    @Test
    public void deveRetornarStatus200AoListarEventos() {
        // ARRANGE
        // Cria uma Page de Evento de mock
        Page<Evento> eventoPage = new PageImpl<>(Collections.singletonList(eventoMock));
        
        // Define o comportamento esperado do Mockito:
        // Quando o findAll() for chamado com qualquer PageRequest, retorne a Page de mock
        when(eventoRepository.findAll(any(PageRequest.class))).thenReturn(eventoPage);

        // ACT
        ResponseEntity<Page<Evento>> response = eventoController.listar();

        // ASSERT
        // Verifica se o status HTTP é OK (200)
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Verifica se o corpo da resposta não está vazio e contém o evento de mock
        assertEquals(1, response.getBody().getContent().size());
        assertEquals(eventoMock.getTitulo(), response.getBody().getContent().get(0).getTitulo());
    }

    @Test
    public void deveRetornarStatus200AoBuscarEventoPorId() {
        // ARRANGE
        Long eventoId = 1L;
        
        // Define o comportamento esperado do Mockito:
        // Quando findById() for chamado com o ID 1L, retorne um Optional contendo o evento de mock
        when(eventoRepository.findById(eventoId)).thenReturn(Optional.of(eventoMock));

        // ACT
        ResponseEntity<Evento> response = eventoController.buscar(eventoId);

        // ASSERT
        // Verifica se o status HTTP é OK (200)
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Verifica se o corpo da resposta contém o evento de mock
        assertEquals(eventoMock.getTitulo(), response.getBody().getTitulo());
    }

    @Test
    public void deveRetornarStatus200AoCadastrarEvento() throws Exception {
        // ARRANGE
        // Define o comportamento esperado do Mockito:
        // Quando save() for chamado com o evento de mock, retorne o próprio evento
        when(eventoRepository.save(any(Evento.class))).thenReturn(eventoMock);

        // ACT
        ResponseEntity<Evento> response = eventoController.cadastrar(eventoMock);

        // ASSERT
        // Verifica se o status HTTP é OK (200)
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Verifica se o corpo da resposta contém o evento de mock
        assertEquals(eventoMock.getTitulo(), response.getBody().getTitulo());
        
        // Verifica se o método save foi realmente chamado no repositório Mock
        Mockito.verify(eventoRepository, Mockito.times(1)).save(eventoMock);
    }
}