package com.restapi.apirestmoviles.model;

import java.time.LocalDateTime;

public record OTPDto(Long id, Long usuarioId, String codigo, LocalDateTime fechaExpiracion) {
}
