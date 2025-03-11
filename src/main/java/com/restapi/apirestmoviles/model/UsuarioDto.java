package com.restapi.apirestmoviles.model;

import java.time.LocalDateTime;

public record UsuarioDto( Long id,
         String nombre,
         String correo,
         String contrasena,
         Long vehiculoId) {
}
