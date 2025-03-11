package com.restapi.apirestmoviles.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record HistorialCargaDto (Long id,
                                 Long usuarioId,
                                 String nombreUsuario,
                                 Long estacionId,
                                 String nombreEstacion,
                                 BigDecimal energiaConsumida,
                                 BigDecimal costoTotal,
                                 LocalDateTime fecha){}
