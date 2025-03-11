package com.restapi.apirestmoviles.model;

import java.time.LocalDateTime;

public record OTPDto(Long id,
                     String nombre,
                     String correo,
                     Long vehiculoId,
                     String marcaVehiculo,
                     String modeloVehiculo,
                     LocalDateTime fechaCreacion) {
}
