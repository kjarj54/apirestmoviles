package com.restapi.apirestmoviles.model;

import java.time.LocalDateTime;

public record PublicacionDto(Long id,
                             Long usuarioId,
                             String nombreUsuario,
                             String tema,
                             String contenido,
                             LocalDateTime fecha) {
}
