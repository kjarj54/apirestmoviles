package com.restapi.apirestmoviles.model;

import java.time.LocalDateTime;

public record UsuarioEventoDto(Long id,
                               Long eventoId,
                               Long usuarioId,
                               LocalDateTime fechaParticipacion) {
}
