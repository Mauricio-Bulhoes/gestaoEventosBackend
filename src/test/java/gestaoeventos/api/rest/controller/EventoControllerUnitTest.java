package gestaoeventos.api.rest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import gestaoeventos.api.rest.dto.EventoRequestDTO;
import gestaoeventos.api.rest.dto.EventoResponseDTO;
import gestaoeventos.api.rest.service.EventoService;

@ExtendWith(MockitoExtension.class)
public class EventoControllerUnitTest {

    @InjectMocks
    private EventoController eventoController;

    @Mock
    private EventoService eventoService;
    
    private EventoRequestDTO eventoRequestDTO;
    private EventoResponseDTO eventoResponseDTO;
    
    
    
    @BeforeEach
    public void setup() {
    	// Cria um DTO de requisição para os testes
        eventoRequestDTO = new EventoRequestDTO();
        eventoRequestDTO.setTitulo("Test Evento");
        eventoRequestDTO.setDescricao("Descrição do evento de teste");
        eventoRequestDTO.setLocal("Test Local");
        eventoRequestDTO.setDataHora(LocalDateTime.now().plusDays(1));

        // Cria um DTO de resposta para os testes
        eventoResponseDTO = new EventoResponseDTO();
        eventoResponseDTO.setId(1L);
        eventoResponseDTO.setTitulo("Test Evento");
        eventoResponseDTO.setDescricao("Descrição do evento de teste");
        eventoResponseDTO.setLocal("Test Local");
        eventoResponseDTO.setDataHora(LocalDateTime.now().plusDays(1));
    }
    
    
    
    @Test
    public void deveRetornarStatus200AoListarEventos() {
        // ARRANGE
        // Cria uma Page de EventoResponseDTO de mock
        Page<EventoResponseDTO> eventoPage = new PageImpl<>(Collections.singletonList(eventoResponseDTO));
        
        // Define o comportamento esperado do Mockito:
        // Quando o listarTodos() for chamado com qualquer PageRequest, retorne a Page de mock
        when(eventoService.listarTodos(any(PageRequest.class))).thenReturn(eventoPage);

        // ACT
        ResponseEntity<Page<EventoResponseDTO>> response = eventoController.listar(0, 5, "dataHora,asc");

        // ASSERT
        // Verifica se o status HTTP é OK (200)
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Verifica se o corpo da resposta não está vazio e contém o evento de mock
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getContent().size());
        assertEquals(eventoResponseDTO.getTitulo(), response.getBody().getContent().get(0).getTitulo());
        
        // Verifica se o método listarTodos foi chamado
        verify(eventoService, times(1)).listarTodos(any(PageRequest.class));
    }
    
    
    
    @Test
    public void deveRetornarStatus200AoBuscarEventoPorId() {
        // ARRANGE
        Long eventoId = 1L;
        
        // Define o comportamento esperado do Mockito:
        // Quando buscarPorId() for chamado com o ID 1L, retorne um Optional contendo o DTO de resposta
        when(eventoService.buscarPorId(eventoId)).thenReturn(Optional.of(eventoResponseDTO));

        // ACT
        ResponseEntity<EventoResponseDTO> response = eventoController.buscar(eventoId);

        // ASSERT
        // Verifica se o status HTTP é OK (200)
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Verifica se o corpo da resposta contém o DTO de resposta
        assertNotNull(response.getBody());
        assertEquals(eventoResponseDTO.getTitulo(), response.getBody().getTitulo());
        assertEquals(eventoResponseDTO.getId(), response.getBody().getId());
        
        // Verifica se o método buscarPorId foi chamado
        verify(eventoService, times(1)).buscarPorId(eventoId);
    }
    
    
    
    @Test
    public void deveRetornarStatus200AoCadastrarEvento() throws Exception {
        // ARRANGE
        // Define o comportamento esperado do Mockito:
        // Quando criar() for chamado com qualquer EventoRequestDTO, retorne o DTO de resposta
        when(eventoService.criar(any(EventoRequestDTO.class))).thenReturn(eventoResponseDTO);

        // ACT
        ResponseEntity<EventoResponseDTO> response = eventoController.cadastrar(eventoRequestDTO);

        // ASSERT
        // Verifica se o status HTTP é CREATED (201)
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        // Verifica se o corpo da resposta contém o DTO de resposta
        assertNotNull(response.getBody());
        assertEquals(eventoResponseDTO.getTitulo(), response.getBody().getTitulo());
        assertEquals(eventoResponseDTO.getId(), response.getBody().getId());
        
        // Verifica se o método criar() foi realmente chamado no serviço Mock
        verify(eventoService, times(1)).criar(any(EventoRequestDTO.class));
    }
    
}