package gestaoeventos.api.rest.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gestaoeventos.api.rest.dto.EventoRequestDTO;
import gestaoeventos.api.rest.dto.EventoResponseDTO;
import gestaoeventos.api.rest.mapper.EventoMapper;
import gestaoeventos.api.rest.model.Evento;
import gestaoeventos.api.rest.repository.EventoRepository;

@Service
public class EventoService {
	
	@Autowired
	private EventoRepository eventoRepository;
	
	@Autowired
    private EventoMapper eventoMapper;
	
	
	
	public Page<EventoResponseDTO> listarTodos(PageRequest pageRequest) {
        Page<Evento> eventos = eventoRepository.findAll(pageRequest);
        return eventos.map(eventoMapper::toResponseDTO);
    }
    
	
    public Optional<EventoResponseDTO> buscarPorId(Long id) {
        return eventoRepository.findById(id)
                .map(eventoMapper::toResponseDTO);
    }
    
    
    @Transactional
    public EventoResponseDTO criar(EventoRequestDTO dto) {
        Evento evento = eventoMapper.toEntity(dto);
        Evento eventoSalvo = eventoRepository.save(evento);
        return eventoMapper.toResponseDTO(eventoSalvo);
    }
    
    
    @Transactional
    public Optional<EventoResponseDTO> atualizar(Long id, EventoRequestDTO dto) {
        return eventoRepository.findById(id)
                .map(evento -> {
                    eventoMapper.updateEntityFromDTO(dto, evento);
                    Evento eventoAtualizado = eventoRepository.save(evento);
                    return eventoMapper.toResponseDTO(eventoAtualizado);
                });
    }
    
    
    @Transactional
    public boolean excluir(Long id) {
        return eventoRepository.findById(id)
                .map(evento -> {
                    evento.setDeleted(true);
                    eventoRepository.save(evento);
                    return true;
                })
                .orElse(false);
    }
	
	
	public Optional<EventoResponseDTO> buscaEventoPorTitulo(String titulo) {
		Evento evento = eventoRepository.findEventByTitulo(titulo);
        return Optional.ofNullable(evento)
                .map(eventoMapper::toResponseDTO);
	}
	
	
}
