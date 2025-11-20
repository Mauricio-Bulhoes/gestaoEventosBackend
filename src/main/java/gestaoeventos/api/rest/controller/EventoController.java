package gestaoeventos.api.rest.controller;

import java.util.Optional;

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
import org.springframework.web.bind.annotation.RestController;

import gestaoeventos.api.rest.model.Evento;
import gestaoeventos.api.rest.repository.EventoRepository;

@RestController
@RequestMapping(value="/evento")
public class EventoController {
	
	@Autowired
	private EventoRepository eventoRepository;
	
	
	public EventoController(EventoRepository repository) {
        this.eventoRepository = repository;
    }

    @GetMapping(value = "/GET/api/events", produces = "application/json")
    public ResponseEntity<Page<Evento>> listar() {
    	PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("titulo"));
    	Page<Evento> list = eventoRepository.findAll(pageRequest);
        return new ResponseEntity<Page<Evento>>(list, HttpStatus.OK);
    }
    
    @GetMapping(value = "/GET/api/events/{id}", produces = "application/json")
    public ResponseEntity<Evento> buscar(@PathVariable (value = "id") Long id) {
    	Optional<Evento> evento = eventoRepository.findById(id);
        return new ResponseEntity<Evento>(evento.get(), HttpStatus.OK);
    }

    @PostMapping(value = "/POST/api/events", produces = "application/json")
    public ResponseEntity<Evento> cadastrar(@RequestBody Evento evento) throws Exception {
        Evento eventoSalvo = eventoRepository.save(evento);
        return new ResponseEntity<Evento>(eventoSalvo, HttpStatus.OK);
    }

    @PutMapping("/PUT/api/events/{id}")
    public ResponseEntity<Evento> atualizar(@PathVariable Long id, @RequestBody Evento evento) {
    	eventoRepository.findById(id);
        evento.setId(id);
        Evento eventoSalvo = eventoRepository.save(evento);
        return new ResponseEntity<Evento>(eventoSalvo, HttpStatus.OK);
    }

    @DeleteMapping("/DELETE/api/events/{id}")
    public void excluir(@PathVariable Long id) {
    	Optional<Evento> evento = eventoRepository.findById(id);
    	evento.get().setDeleted(true);
    	eventoRepository.save(evento.get());
    	//eventoRepository.deleteById(id);
    }
	
}
