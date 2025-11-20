package gestaoeventos.api.rest.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public class EventoRequestDTO {
    
    @NotBlank(message = "O titulo nao pode estar em branco")
    @Size(min = 1, max = 100, message = "O titulo deve ter entre {min} e {max} caracteres")
    private String titulo;
    
    @Size(max = 1000, message = "A descricao deve ter no maximo {max} caracteres")
    private String descricao;
    
    @Future(message = "A data e hora deve ser futura")
    private LocalDateTime dataHora;
    
    @NotBlank(message = "O local nao pode estar em branco")
    @Size(min = 1, max = 200, message = "O local deve ter entre {min} e {max} caracteres")
    private String local;
    
    
    
    public EventoRequestDTO() {}
    
    
    public EventoRequestDTO(String titulo, String descricao, LocalDateTime dataHora, String local) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataHora = dataHora;
        this.local = local;
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