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
    private String comentario;

    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();    public CalificacionEstacion() {
    }
}