package com.restapi.apirestmoviles.model;

import java.time.LocalDateTime;

public record EventoDto(Long id,
                        Long usuarioCreadorId,
                        String nombreCreador,
                        String titulo,
                        String descripcion,
                        LocalDateTime fechaEvento,
                        String ubicacion,
                        String estado,
                        LocalDateTime fechaCreacion,
                        Integer cantidadParticipantes) {
}
