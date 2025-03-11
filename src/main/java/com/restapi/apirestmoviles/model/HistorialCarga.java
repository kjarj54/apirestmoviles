package com.restapi.apirestmoviles.model;


import jakarta.persistence.*;
import lombok.Data;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "historial_cargas")
public class HistorialCarga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "estacion_id", nullable = false)
    private EstacionCarga estacion;

    @Column(name = "energia_consumida", precision = 6, scale = 2)
    private BigDecimal energiaConsumida;

    @Column(name = "costo_total", precision = 6, scale = 2)
    private BigDecimal costoTotal;

    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();

    public HistorialCarga() {
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

    public EstacionCarga getEstacion() {
        return estacion;
    }

    public void setEstacion(EstacionCarga estacion) {
        this.estacion = estacion;
    }

    public BigDecimal getEnergiaConsumida() {
        return energiaConsumida;
    }

    public void setEnergiaConsumida(BigDecimal energiaConsumida) {
        this.energiaConsumida = energiaConsumida;
    }

    public BigDecimal getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(BigDecimal costoTotal) {
        this.costoTotal = costoTotal;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
