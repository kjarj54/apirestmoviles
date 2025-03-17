package com.restapi.apirestmoviles.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RutaDto(Long id,
                      Long usuarioId,
                      String origen,
                      String destino,
                      BigDecimal distanciaKm,
                      Integer tiempoEstimadoMin,
                      String estacionesIntermedias,
                      BigDecimal costoEstimado,
                      BigDecimal emisionesAhorradas,
                      LocalDateTime fechaPlanificacion,
                      LocalDateTime fechaCompletada,
                      String estado) {
}
