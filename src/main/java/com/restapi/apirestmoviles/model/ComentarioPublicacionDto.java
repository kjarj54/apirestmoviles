package com.restapi.apirestmoviles.model;

import java.time.LocalDateTime;

public record ComentarioPublicacionDto(Long id,
                                       Long publicacionId,
                                       String temaPublicacion,
                                       Long usuarioId,
                                       String nombreUsuario,
                                       String contenido,
                                       LocalDateTime fecha) {
}
