package gestaoeventos.api.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gestaoeventos.api.rest.model.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

}
