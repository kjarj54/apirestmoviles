package com.restapi.apirestmoviles.model;

import java.time.LocalDateTime;

public record CalificacionEstacionDto(Long id,
                                      Long usuarioId,
                                      Long estacionId,
                                      Integer calificacion,
                                      String comentario,
                                      LocalDateTime fecha,
                                      String nombreUsuario) {
}
