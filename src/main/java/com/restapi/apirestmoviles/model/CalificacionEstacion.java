package com.restapi.apirestmoviles.model;


import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "calificaciones_estaciones")
public class CalificacionEstacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;    @ManyToOne
    @JoinColumn(name = "estacion_id", nullable = false)
    private EstacionCarga estacion;

    @Column(nullable = false)
    private Integer calificacion;

    @Column(columnDefinition = "TEXT")
    private String comentario;    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();    public CalificacionEstacion() {
    }

    // Getters and setters (manually added because of Lombok issues)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public EstacionCarga getEstacion() {
        return estacion;
    }

    public void setEstacion(EstacionCarga estacion) {
        this.estacion = estacion;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}