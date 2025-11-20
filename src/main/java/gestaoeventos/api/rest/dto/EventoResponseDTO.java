package gestaoeventos.api.rest.dto;

import java.time.LocalDateTime;

public class EventoResponseDTO {
    
    private Long id;
    private String titulo;
    private String descricao;
    private LocalDateTime dataHora;
    private String local;
    
    
    
    public EventoResponseDTO() {}
    
    
    public EventoResponseDTO(Long id, String titulo, String descricao, LocalDateTime dataHora, String local) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataHora = dataHora;
        this.local = local;
    }
    
    
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
    
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    
    public LocalDateTime getDataHora() {
        return dataHora;
    }
    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
    
    
    public String getLocal() {
        return local;
    }
    public void setLocal(String local) {
        this.local = local;
    }
    
}