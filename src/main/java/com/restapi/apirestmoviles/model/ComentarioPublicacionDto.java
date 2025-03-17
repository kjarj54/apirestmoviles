package com.restapi.apirestmoviles.model;

import java.time.LocalDateTime;

public record ComentarioPublicacionDto(Long id,
                                       Long publicacionId,
                                       Long usuarioId,
                                       String contenido,
                                       LocalDateTime fecha) {
}
