package com.restapi.apirestmoviles.model;

import java.time.LocalDateTime;

public record EstacionCargaDto(
        Long id,
        String nombre,
        Double latitud,
        Double longitud,
        String direccion,
        String tipoCargador,
        Integer potencia,
        Double tarifa,
        Boolean disponibilidad,
        String horario) {
}