package com.restapi.apirestmoviles.model;

import java.time.LocalDateTime;

public record PublicacionDto(Long id,
                             Long usuarioId,
                             String tema,
                             String contenido,
                             LocalDateTime fecha) {
}
