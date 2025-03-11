package com.restapi.apirestmoviles.model;


public record UsuarioDto( Long id,
         String nombre,
         String correo,
         String contrasena,
         Long vehiculoId) {
}
