package com.restapi.apirestmoviles.model;

import java.time.LocalDateTime;

public record UsuarioEventoDto(Long id,
                               Long eventoId,
                               String tituloEvento,
                               Long usuarioId,
                               String nombreUsuario,
                               LocalDateTime fechaParticipacion) {
}
