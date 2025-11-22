package gestaoeventos.api.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gestaoeventos.api.rest.dto.EventoRequestDTO;
import gestaoeventos.api.rest.dto.EventoResponseDTO;
import gestaoeventos.api.rest.service.EventoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value="/evento")
@Tag(name = "Eventos", description = "API para gerenciar eventos")
public class EventoController {
	
	@Autowired
	private EventoService eventoService;
	
	

	@Operation(summary = "Listar Eventos", description = "Retorna uma lista paginada de eventos ordenados por título")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Lista de eventos retornada com sucesso",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
	})
	@GetMapping(value = "/GET/api/events", produces = "application/json")
    public ResponseEntity<Page<EventoResponseDTO>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "dataHora,asc") String sort) {
		
		// Processa o parâmetro de ordenação
	    String[] sortParams = sort.split(",");
	    String sortField = sortParams[0];
	    String sortDirection = sortParams.length > 1 ? sortParams[1] : "asc";
	    
	    // Define a direção da ordenação
	    Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") 
	        ? Sort.Direction.DESC 
	        : Sort.Direction.ASC;
		
	    // Cria o PageRequest com os parâmetros recebidos
	    PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortField));
    	Page<EventoResponseDTO> list = eventoService.listarTodos(pageRequest);
        return new ResponseEntity<Page<EventoResponseDTO>>(list, HttpStatus.OK);
    }
	
    
	@Operation(summary = "Buscar evento por ID", description = "Retorna um evento específico pelo seu ID")
    @ApiResponses(value = {
    	@ApiResponse(responseCode = "200", description = "Evento encontrado",
    		content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventoResponseDTO.class))),
    	@ApiResponse(responseCode = "404", description = "Evento não encontrado", content = @Content)
    })
    @GetMapping(value = "/GET/api/events/{id}", produces = "application/json")
    public ResponseEntity<EventoResponseDTO> buscar(@PathVariable (value = "id") Long id) {
    	return eventoService.buscarPorId(id)
                .map(evento -> ResponseEntity.status(HttpStatus.OK).body(evento))
                .orElse(ResponseEntity.notFound().build());
    }
	
	
	@Operation(summary = "Cadastrar novo evento", description = "Cria um novo evento no sistema")
    @ApiResponses(value = {
    	@ApiResponse(responseCode = "201", description = "Evento criado com sucesso",
    		content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventoResponseDTO.class))),
    	@ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @PostMapping(value = "/POST/api/events", produces = "application/json")
    public ResponseEntity<EventoResponseDTO> cadastrar(@Valid @RequestBody EventoRequestDTO eventoRequestDto) throws Exception {
        EventoResponseDTO response = eventoService.criar(eventoRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
	
	
	@Operation(summary = "Atualizar Evento", description = "Atualiza os dados de um evento específico")
    @ApiResponses(value = {
    	@ApiResponse(responseCode = "200", description = "Evento atualizado com sucesso",
    		content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventoResponseDTO.class))),
    	@ApiResponse(responseCode = "404", description = "Evento não encontrado", content = @Content),
    	@ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @PutMapping("/PUT/api/events/{id}")
    public ResponseEntity<EventoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody EventoRequestDTO eventoRequestDto) {
    	return eventoService.atualizar(id, eventoRequestDto)
                .map(evento -> ResponseEntity.status(HttpStatus.OK).body(evento))
                .orElse(ResponseEntity.notFound().build());
    }
	
	
	@Operation(summary = "Excluir Evento", description = "Realiza exclusão de um evento (feito com soft delete, não exclui fisicamente)")
    @ApiResponses(value = {
    	@ApiResponse(responseCode = "204", description = "Evento excluído com sucesso", content = @Content),
    	@ApiResponse(responseCode = "404", description = "Evento não encontrado", content = @Content)
    })
    @DeleteMapping("/DELETE/api/events/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
    	boolean deletado = eventoService.excluir(id);
        return deletado ? ResponseEntity.noContent().build() 
                        : ResponseEntity.notFound().build();
    }
    
	
	@Operation(summary = "Buscar evento por título", description = "Busca um evento pelo título")
    @ApiResponses(value = {
    	@ApiResponse(responseCode = "200", description = "Evento encontrado",
    		content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventoResponseDTO.class))),
    	@ApiResponse(responseCode = "404", description = "Evento não encontrado", content = @Content)
    })
    @GetMapping(value = "/GET/api/events/buscarPorTitulo", produces = "application/json")
    public ResponseEntity<EventoResponseDTO> buscarPorTitulo(@RequestParam (value = "titulo") String titulo) {
    	return eventoService.buscaEventoPorTitulo(titulo)
                .map(evento -> ResponseEntity.status(HttpStatus.OK).body(evento))
                .orElse(ResponseEntity.notFound().build());
    }
	
}
