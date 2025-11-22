package gestaoeventos.api.rest.mapper;

import gestaoeventos.api.rest.dto.EventoRequestDTO;
import gestaoeventos.api.rest.dto.EventoResponseDTO;
import gestaoeventos.api.rest.model.Evento;
import org.springframework.stereotype.Component;

@Component
public class EventoMapper { //Esta classe é responsável por converter o DTO em Evento
    
	
    public Evento toEntity(EventoRequestDTO dto) {
        Evento evento = new Evento();
        evento.setTitulo(dto.getTitulo());
        evento.setDescricao(dto.getDescricao());
        evento.setDataHora(dto.getDataHora());
        evento.setLocal(dto.getLocal());
        return evento;
    }
    
    
    public EventoResponseDTO toResponseDTO(Evento evento) {
        return new EventoResponseDTO(
            evento.getId(),
            evento.getTitulo(),
            evento.getDescricao(),
            evento.getDataHora(),
            evento.getLocal(),
            evento.getCreatedAt(),
            evento.getUpdatedAt()
        );
    }
    
    
    public void updateEntityFromDTO(EventoRequestDTO dto, Evento evento) {
        evento.setTitulo(dto.getTitulo());
        evento.setDescricao(dto.getDescricao());
        evento.setDataHora(dto.getDataHora());
        evento.setLocal(dto.getLocal());
        // Aqui não precisamos setar created_at e updated_at, eles são gerenciados automaticamente
    }
    
    
}