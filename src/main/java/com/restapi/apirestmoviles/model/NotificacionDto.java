package com.restapi.apirestmoviles.model;

import java.time.LocalDateTime;

public record NotificacionDto (Long id,
                               Long usuarioId,
                               String mensaje,
                               String tipo,
                               Boolean leido,
                               LocalDateTime fecha){}
