package com.restapi.apirestmoviles.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record HistorialCargaDto (Long id,
                                 Long usuarioId,
                                 Long estacionId,
                                 BigDecimal energiaConsumida,
                                 BigDecimal costoTotal,
                                 LocalDateTime fecha){}
