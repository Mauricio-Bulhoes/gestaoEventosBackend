package gestaoeventos.api.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gestaoeventos.api.rest.model.Evento;
import gestaoeventos.api.rest.repository.EventoRepository;

@Service
public class EventoService {
	
	@Autowired
	private EventoRepository eventoRepository;
	
	
	public Evento buscaEventoPorTitulo(String titulo) {
		return eventoRepository.findEventByTitulo(titulo);
	}

}
