package com.restapi.apirestmoviles.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "rutas")
public class Ruta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false, length = 255)
    private String origen;

    @Column(nullable = false, length = 255)
    private String destino;

    @Column(name = "distancia_km", precision = 10, scale = 2)
    private BigDecimal distanciaKm;

    @Column(name = "tiempo_estimado_min")
    private Integer tiempoEstimadoMin;

    @Column(name = "estaciones_intermedias", columnDefinition = "json")
    private String estacionesIntermedias;

    @Column(name = "costo_estimado", precision = 10, scale = 2)
    private BigDecimal costoEstimado;

    @Column(name = "emisiones_ahorradas", precision = 10, scale = 2)
    private BigDecimal emisionesAhorradas;

    @Column(name = "fecha_planificacion", nullable = false)
    private LocalDateTime fechaPlanificacion = LocalDateTime.now();

    @Column(name = "fecha_completada")
    private LocalDateTime fechaCompletada;

    @Column(length = 50)
    private String estado = "Planificada";

    public Ruta() {
    }

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

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public BigDecimal getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(BigDecimal distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    public Integer getTiempoEstimadoMin() {
        return tiempoEstimadoMin;
    }

    public void setTiempoEstimadoMin(Integer tiempoEstimadoMin) {
        this.tiempoEstimadoMin = tiempoEstimadoMin;
    }

    public String getEstacionesIntermedias() {
        return estacionesIntermedias;
    }

    public void setEstacionesIntermedias(String estacionesIntermedias) {
        this.estacionesIntermedias = estacionesIntermedias;
    }

    public BigDecimal getCostoEstimado() {
        return costoEstimado;
    }

    public void setCostoEstimado(BigDecimal costoEstimado) {
        this.costoEstimado = costoEstimado;
    }

    public BigDecimal getEmisionesAhorradas() {
        return emisionesAhorradas;
    }

    public void setEmisionesAhorradas(BigDecimal emisionesAhorradas) {
        this.emisionesAhorradas = emisionesAhorradas;
    }

    public LocalDateTime getFechaCompletada() {
        return fechaCompletada;
    }

    public void setFechaCompletada(LocalDateTime fechaCompletada) {
        this.fechaCompletada = fechaCompletada;
    }

    public LocalDateTime getFechaPlanificacion() {
        return fechaPlanificacion;
    }

    public void setFechaPlanificacion(LocalDateTime fechaPlanificacion) {
        this.fechaPlanificacion = fechaPlanificacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}