package com.restapi.apirestmoviles.model;

import java.time.LocalDateTime;

public record CalificacionEstacionDto(Long id,
                                      Long usuarioId,
                                      String nombreUsuario,
                                      Long estacionId,
                                      String nombreEstacion,
                                      Integer calificacion,
                                      LocalDateTime fecha) {
}
