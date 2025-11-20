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
import jakarta.validation.Valid;

@RestController
@RequestMapping(value="/evento")
public class EventoController {
	
	@Autowired
	private EventoService eventoService;
	
	

    @GetMapping(value = "/GET/api/events", produces = "application/json")
    public ResponseEntity<Page<EventoResponseDTO>> listar() {
    	PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("titulo"));
    	Page<EventoResponseDTO> list = eventoService.listarTodos(pageRequest);
        return new ResponseEntity<Page<EventoResponseDTO>>(list, HttpStatus.OK);
    }
    
    @GetMapping(value = "/GET/api/events/{id}", produces = "application/json")
    public ResponseEntity<EventoResponseDTO> buscar(@PathVariable (value = "id") Long id) {
    	return eventoService.buscarPorId(id)
                .map(evento -> ResponseEntity.status(HttpStatus.OK).body(evento))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/POST/api/events", produces = "application/json")
    public ResponseEntity<EventoResponseDTO> cadastrar(@Valid @RequestBody EventoRequestDTO eventoRequestDto) throws Exception {
        EventoResponseDTO response = eventoService.criar(eventoRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/PUT/api/events/{id}")
    public ResponseEntity<EventoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody EventoRequestDTO eventoRequestDto) {
    	return eventoService.atualizar(id, eventoRequestDto)
                .map(evento -> ResponseEntity.status(HttpStatus.OK).body(evento))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/DELETE/api/events/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
    	boolean deletado = eventoService.excluir(id);
        return deletado ? ResponseEntity.noContent().build() 
                        : ResponseEntity.notFound().build();
    }
    
    @GetMapping(value = "/GET/api/events/buscarPorTitulo", produces = "application/json")
    public ResponseEntity<EventoResponseDTO> buscarPorTitulo(@RequestParam (value = "titulo") String titulo) {
    	return eventoService.buscaEventoPorTitulo(titulo)
                .map(evento -> ResponseEntity.status(HttpStatus.OK).body(evento))
                .orElse(ResponseEntity.notFound().build());
    }
	
}
